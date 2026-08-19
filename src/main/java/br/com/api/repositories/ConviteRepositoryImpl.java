package br.com.api.repositories;

import br.com.api.domain.dtos.convite.ResgatarConvite;
import br.com.api.domain.entities.subcollections.Convite;
import br.com.api.domain.entities.subcollections.MembroCampanha;
import br.com.api.domain.enums.StatusConvite;
import br.com.api.domain.enums.StatusMembro;
import br.com.api.domain.enums.TipoMembro;
import br.com.api.domain.exceptions.ConflictException;
import br.com.api.domain.mappers.ConviteMapper;
import br.com.api.repositories.interfaces.ConviteRepository;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
public class ConviteRepositoryImpl implements ConviteRepository {

    private static final String CAMPANHAS_COLLECTION = "campanhas";
    private static final String CONVITES_SUBCOLLECTION = "convites";
    private static final String MEMBROS_SUBCOLLECTION = "membro_campanhas";

    @Inject
    Firestore db;

    @Override
    public void criar(String idCampanha, Convite convite) throws ExecutionException, InterruptedException {
        convitesDaCampanha(idCampanha)
                .document(convite.getToken())
                .set(convite)
                .get();
    }

    @Override
    public ResgatarConvite resgatar(String token, String idUsuario, String nomeUsuario) throws ExecutionException, InterruptedException {
        ApiFuture<ResgatarConvite> future = db.runTransaction(transaction -> {

            QuerySnapshot conviteSnapshot = transaction.get(
                    db.collectionGroup(CONVITES_SUBCOLLECTION)
                            .whereEqualTo("token", token)
                            .limit(1)
            ).get();

            if (conviteSnapshot.isEmpty()) {
                throw new NotFoundException("Convite não encontrado");
            }

            DocumentSnapshot conviteDoc = conviteSnapshot.getDocuments().get(0);
            Convite convite = conviteDoc.toObject(Convite.class);
            DocumentReference conviteRef = conviteDoc.getReference();
            DocumentReference campanhaRef = conviteRef.getParent().getParent();
            String idCampanha = campanhaRef.getId();

            if (convite.getStatus() != StatusConvite.ATIVO) {
                throw new ForbiddenException("Convite já utilizado ou expirado");
            }

            if (estaExpirado(convite.getCriadoEm())) {
                transaction.update(conviteRef, "status", StatusConvite.EXPIRADO);
                throw new ForbiddenException("Convite expirado");
            }

            DocumentReference membroRef = campanhaRef.collection(MEMBROS_SUBCOLLECTION).document(idUsuario);
            DocumentSnapshot membroSnapshot = transaction.get(membroRef).get();

            if (membroSnapshot.exists()) {
                throw new ConflictException("Usuário já é membro desta campanha");
            }

            MembroCampanha membro = new MembroCampanha();
            membro.setNomeUsuario(nomeUsuario);
            membro.setTipoMembro(TipoMembro.JOGADOR);
            membro.setStatus(StatusMembro.ATIVO);

            transaction.set(membroRef, membro);
            transaction.update(conviteRef, "status", StatusConvite.EXPIRADO);

            membro.setIdUsuario(idUsuario);

            return new ResgatarConvite(idCampanha, membro);
        });

        try {
            return future.get();
        } catch (ExecutionException e) {
            throw desembrulhar(e);
        }
    }

    private RuntimeException desembrulhar(ExecutionException e) {
        Throwable causa = e.getCause();

        if (causa instanceof RuntimeException re) {
            return re;
        }

        return new RuntimeException("Erro ao resgatar convite", causa != null ? causa : e);
    }

    private boolean estaExpirado(Timestamp criadoEm) {
        var limite = criadoEm.toDate().toInstant().plus(ConviteMapper.VALIDADE_DIAS, ChronoUnit.DAYS);
        return Instant.now().isAfter(limite);
    }

    private CollectionReference convitesDaCampanha(String idCampanha) {
        return db.collection(CAMPANHAS_COLLECTION)
                .document(idCampanha)
                .collection(CONVITES_SUBCOLLECTION);
    }
}