package br.com.api.repositories;

import br.com.api.domain.entities.subcollections.MembroCampanha;
import br.com.api.repositories.interfaces.MembroRepository;
import com.google.cloud.firestore.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
public class MembroRepositoryImpl implements MembroRepository {

    private static final String CAMPANHAS_COLLECTION = "campanhas";
    private static final String MEMBROS_SUBCOLLECTION = "membro_campanhas";

    @Inject
    Firestore firestore;

    @Override
    public void adicionar(String idCampanha, String idUsuario, MembroCampanha membroCampanha) throws ExecutionException, InterruptedException {
        membrosDaCampanha(idCampanha)
                .document(idUsuario)
                .set(membroCampanha)
                .get();
    }

    @Override
    public Optional<MembroCampanha> obterPorCampanhaEUsuario(String idCampanha, String idUsuario) throws ExecutionException, InterruptedException {
        DocumentSnapshot snapshot = membrosDaCampanha(idCampanha)
                .document(idUsuario)
                .get()
                .get();

        return mapSnapshot(snapshot);
    }

    @Override
    public List<MembroCampanha> obterTodosPorCampanha(String idCampanha) throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> documentos = membrosDaCampanha(idCampanha)
                .get()
                .get()
                .getDocuments();

        return documentos.stream()
                .map(this::toMembroComId)
                .toList();
    }

    @Override
    public void remover(String idCampanha, String idUsuario) throws ExecutionException, InterruptedException {
        membrosDaCampanha(idCampanha)
                .document(idUsuario)
                .delete()
                .get();
    }

    @Override
    public void deletarPorCampanha(String idCampanha) throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> documentos = membrosDaCampanha(idCampanha)
                .get()
                .get()
                .getDocuments();

        if (documentos.isEmpty()) {
            return;
        }

        int tamanhoLote = 500;
        for (int i = 0; i < documentos.size(); i += tamanhoLote) {
            WriteBatch batch = firestore.batch();
            documentos.subList(i, Math.min(i + tamanhoLote, documentos.size()))
                    .forEach(doc -> batch.delete(doc.getReference()));
            batch.commit().get();
        }
    }

    private CollectionReference membrosDaCampanha(String idCampanha) {
        return firestore.collection(CAMPANHAS_COLLECTION)
                .document(idCampanha)
                .collection(MEMBROS_SUBCOLLECTION);
    }

    private Optional<MembroCampanha> mapSnapshot(DocumentSnapshot snapshot) {
        if (!snapshot.exists()) {
            return Optional.empty();
        }
        MembroCampanha membro = snapshot.toObject(MembroCampanha.class);
        if (membro != null) {
            membro.setIdUsuario(snapshot.getId());
        }
        return Optional.ofNullable(membro);
    }

    private MembroCampanha toMembroComId(QueryDocumentSnapshot doc) {
        MembroCampanha membro = doc.toObject(MembroCampanha.class);
        membro.setIdUsuario(doc.getId());
        return membro;
    }
}