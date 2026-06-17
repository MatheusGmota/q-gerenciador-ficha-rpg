package br.com.api.repositories;

import br.com.api.domain.entities.Ameaca;
import br.com.api.domain.model.Pericia;
import br.com.api.repositories.interfaces.AmeacaRepository;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Slf4j
@ApplicationScoped
public class AmeacaRepositoryImpl implements AmeacaRepository {
    private static final String COLLECTION_NAME = "ameacas";
    private static final int MAX_LIMIT_DOCUMENTS = 10;

    @Inject
    public Firestore db;

    private CollectionReference getCollection() {
        return db.collection(COLLECTION_NAME);
    }

    private DocumentReference getFichaDocument(String idFicha) {
        return getCollection().document(idFicha);
    }

    public boolean excedeuLimiteMaxFichas(String idUsuario)  throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = getCollection()
                .whereEqualTo("idUsuario", idUsuario)
                .get();

        List<QueryDocumentSnapshot> documents = query.get().getDocuments();

        if (documents.isEmpty()) return false;

        return documents.size() == MAX_LIMIT_DOCUMENTS;

    }

    @Override
    public void atualizarPericia(String idFicha, String nomePericia, Pericia pericia) {
        getCollection()
                .document(idFicha)
                .update("pericias." + nomePericia, pericia);
    }

    @Override
    public List<Ameaca> obterFichasPorIdUsuario(String idUsuario) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = getCollection()
                .whereEqualTo("idUsuario", idUsuario)
                .orderBy("criadoEm")
                .limit(MAX_LIMIT_DOCUMENTS)
                .get();

        QuerySnapshot snapshot = query.get();
        if (snapshot.isEmpty()) return List.of();

        return snapshot.getDocuments()
                .stream()
                .map(doc -> doc.toObject(Ameaca.class))
                .toList();
    }

    @Override
    public List<Ameaca> obterTodasFichas() throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = getCollection().get();

        QuerySnapshot snapshot = query.get();
        if (snapshot.isEmpty()) return List.of();

        return snapshot
                .getDocuments()
                .stream().map(doc -> doc.toObject(Ameaca.class))
                .toList();
    }

    @Override
    public Optional<Ameaca> obterPorId(String idFicha) throws ExecutionException, InterruptedException {
        DocumentSnapshot doc = getFichaDocument(idFicha).get().get();

        if (!doc.exists()) return Optional.empty();
        return Optional.ofNullable(doc.toObject(Ameaca.class));
    }

    @Override
    public Ameaca persistirFicha(Ameaca agente) throws ExecutionException, InterruptedException {
        DocumentReference doc = getCollection().add(agente).get();
        return doc.get().get().toObject(Ameaca.class);
    }

    @Override
    public void alterarFicha(String idFicha, Map<String, Object> campos) throws ExecutionException, InterruptedException {
        getFichaDocument(idFicha).update(campos).get();
    }

    @Override
    public void deletarFicha(String idFicha) throws ExecutionException, InterruptedException {
        DocumentReference document = getFichaDocument(idFicha);

        deletarSubCollections(document, 2);

        document.delete().get();
    }

    private void deletarSubCollections(DocumentReference document, int batchSize) throws ExecutionException, InterruptedException {
        for (CollectionReference collection : document.listCollections()) {
            WriteBatch batch = db.batch();

            ApiFuture<QuerySnapshot> future = collection.limit(batchSize).get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            for (QueryDocumentSnapshot doc : documents) {
                batch.delete(doc.getReference());
            }

            batch.commit().get();

            if (documents.size() >= batchSize) {
                deletarSubCollections(document, batchSize);
            }
        }
    }

}
