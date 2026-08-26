package br.com.api.repositories;

import br.com.api.domain.entities.subcollections.FichaVinculada;
import br.com.api.domain.enums.TipoFicha;
import br.com.api.domain.exceptions.ConflictException;
import br.com.api.repositories.interfaces.FichaVinculadaRepository;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
public class FichaVinculadaRepositoryImpl implements FichaVinculadaRepository {

    private static final String CAMPANHAS_COLLECTION = "campanhas";
    private static final String FICHAS_SUBCOLLECTION = "fichas_vinculadas";
    private static final int MAX_FICHAS_PADRAO = 20;

    @Inject
    Firestore db;

    @Override
    public FichaVinculada vincularComValidacao(String idCampanha, FichaVinculada vinculo, boolean ehMestre) throws ExecutionException, InterruptedException {
        ApiFuture<FichaVinculada> future = db.runTransaction(transaction -> {
            DocumentReference campanhaRef = db.collection(CAMPANHAS_COLLECTION).document(idCampanha);
            DocumentReference vinculoRef = fichasDaCampanha(idCampanha).document(vinculo.getIdFicha());

            DocumentSnapshot campanhaSnap = transaction.get(campanhaRef).get();
            if (!campanhaSnap.exists()) {
                throw new NotFoundException("Campanha não encontrada");
            }

            DocumentSnapshot vinculoSnap = transaction.get(vinculoRef).get();
            if (vinculoSnap.exists()) {
                throw new ConflictException("Ficha já vinculada a esta campanha");
            }

            QuerySnapshot todasQuery = transaction.get(fichasDaCampanha(idCampanha)).get();
            int totalAtual = todasQuery.size();

            Long maxFichasCampanha = campanhaSnap.getLong("maxFichas");
            int maxFichas = maxFichasCampanha != null && maxFichasCampanha > 0
                    ? maxFichasCampanha.intValue()
                    : MAX_FICHAS_PADRAO;

            if (totalAtual >= maxFichas) {
                throw new ForbiddenException("Limite máximo de fichas da campanha atingido");
            }

            if (!ehMestre && vinculo.getTipo() == TipoFicha.AGENTE) {
                boolean jaTemAgente = todasQuery.getDocuments().stream()
                        .anyMatch(doc ->
                                vinculo.getIdUsuario().equals(doc.getString("idUsuario"))
                                        && TipoFicha.AGENTE.name().equals(doc.getString("tipo"))
                        );

                if (jaTemAgente) {
                    throw new ForbiddenException("Jogador só pode vincular uma ficha de Agente por campanha");
                }
            }

            transaction.set(vinculoRef, vinculo);
            return vinculo;
        });

        try {
            return future.get();
        } catch (ExecutionException e) {
            throw desembrulhar(e);
        }
    }

    @Override
    public Optional<FichaVinculada> obterPorId(String idCampanha, String idFicha) throws ExecutionException, InterruptedException {
        DocumentSnapshot snapshot = fichasDaCampanha(idCampanha)
                .document(idFicha)
                .get()
                .get();

        return mapSnapshot(snapshot);
    }

    @Override
    public List<FichaVinculada> obterTodas(String idCampanha) throws ExecutionException, InterruptedException {
        return fichasDaCampanha(idCampanha)
                .get().get()
                .getDocuments()
                .stream()
                .map(this::toFichaComId)
                .toList();
    }

    @Override
    public List<FichaVinculada> obterPorUsuario(String idCampanha, String idUsuario) throws ExecutionException, InterruptedException {
        return fichasDaCampanha(idCampanha)
                .whereEqualTo("idUsuario", idUsuario)
                .get().get()
                .getDocuments()
                .stream()
                .map(this::toFichaComId)
                .toList();
    }

    @Override
    public void desvincular(String idCampanha, String idFicha) throws ExecutionException, InterruptedException {
        fichasDaCampanha(idCampanha)
                .document(idFicha)
                .delete()
                .get();
    }

    @Override
    public void desvincularTodasDaCampanha(String idCampanha) throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> documentos = fichasDaCampanha(idCampanha)
                .get().get()
                .getDocuments();

        if (documentos.isEmpty()) return;

        int tamanhoLote = 500;
        for (int i = 0; i < documentos.size(); i += tamanhoLote) {
            WriteBatch batch = db.batch();
            documentos.subList(i, Math.min(i + tamanhoLote, documentos.size()))
                    .forEach(doc -> batch.delete(doc.getReference()));
            batch.commit().get();
        }
    }

    private CollectionReference fichasDaCampanha(String idCampanha) {
        return db.collection(CAMPANHAS_COLLECTION)
                .document(idCampanha)
                .collection(FICHAS_SUBCOLLECTION);
    }

    private Optional<FichaVinculada> mapSnapshot(DocumentSnapshot snapshot) {
        if (!snapshot.exists()) return Optional.empty();
        FichaVinculada f = snapshot.toObject(FichaVinculada.class);
        if (f != null) f.setIdFicha(snapshot.getId());
        return Optional.ofNullable(f);
    }

    private FichaVinculada toFichaComId(QueryDocumentSnapshot doc) {
        FichaVinculada f = doc.toObject(FichaVinculada.class);
        f.setIdFicha(doc.getId());
        return f;
    }

    /**
     * O SDK do Firestore encapsula qualquer exceção lançada dentro da
     * transação em uma ExecutionException. Sem esse tratamento, o
     * GlobalExceptionHandler não veria o tipo real (NotFound/Forbidden/Conflict).
     */
    private RuntimeException desembrulhar(ExecutionException e) {
        Throwable causa = e.getCause();
        if (causa instanceof RuntimeException re) {
            return re;
        }
        return new RuntimeException("Erro ao vincular ficha", causa != null ? causa : e);
    }
}