package br.com.api.repositories;

import br.com.api.domain.entities.Campanha;
import br.com.api.repositories.interfaces.CampanhaRepository;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
public class CampanhaRepositoryImpl implements CampanhaRepository {
    private static final String COLLECTION_NAME = "campanhas";

    @Inject
    public Firestore db;

    private CollectionReference getCollection() {
        return db.collection(COLLECTION_NAME);
    }

    private DocumentReference getCampanhaDocument(String idCampanha) {
        return getCollection().document(idCampanha);
    }

    @Override
    public List<Campanha> obterPorIdUsuario(String idUsuario) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = getCollection()
                .whereEqualTo("idMestre", idUsuario)
                .orderBy("criadoEm")
                .get();

        QuerySnapshot snapshot = query.get();
        if (snapshot.isEmpty()) return List.of();

        return snapshot.getDocuments()
                .stream()
                .map(doc -> doc.toObject(Campanha.class))
                .toList();
    }

    @Override
    public List<Campanha> obterTodas() throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = getCollection().get();

        QuerySnapshot snapshot = query.get();
        if (snapshot.isEmpty()) return List.of();

        return snapshot
                .getDocuments()
                .stream().map(doc -> doc.toObject(Campanha.class))
                .toList();
    }

    @Override
    public Optional<Campanha> obterPorId(String id) throws ExecutionException, InterruptedException {
        DocumentSnapshot doc = getCampanhaDocument(id).get().get();

        if (!doc.exists()) return Optional.empty();
        return Optional.ofNullable(doc.toObject(Campanha.class));
    }

    @Override
    public Campanha persistir(Campanha campanha) throws ExecutionException, InterruptedException {
        DocumentReference doc = getCollection().add(campanha).get();
        return doc.get().get().toObject(Campanha.class);
    }

    @Override
    public void alterar(String idCampanha, Map<String, Object> campos) throws ExecutionException, InterruptedException {
        getCampanhaDocument(idCampanha).update(campos).get();
    }

    @Override
    public void deletar(String idCampanha) throws ExecutionException, InterruptedException {
        DocumentReference document = getCampanhaDocument(idCampanha);
        document.delete().get();
    }
}
