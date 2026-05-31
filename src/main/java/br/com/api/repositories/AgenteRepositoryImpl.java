package br.com.api.repositories;

import br.com.api.domain.entities.Agente;
import br.com.api.domain.entities.subcollections.Habilidade;
import br.com.api.repositories.interfaces.AgenteRepository;
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
public class AgenteRepositoryImpl implements AgenteRepository {
    private static final String COLLECTION_NAME = "fichas";
    private static final int MAX_LIMIT_DOCUMENTS = 10;

    @Inject
    public Firestore db;

    private CollectionReference getCollection() {
        return db.collection(COLLECTION_NAME);
    }

    private DocumentReference getFichasCollection(String idFicha) {
        return getCollection().document(idFicha);
    }

    private CollectionReference getHabilidadesCollection(String idFicha) {
        return getFichasCollection(idFicha)
                .collection(Habilidade.COLLECTION_NAME);
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
    public List<Agente> obterFichasPorIdUsuario(String idUsuario) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = getCollection()
                .whereEqualTo("idUsuario", idUsuario)
                .orderBy("criadoEm")
                .limit(MAX_LIMIT_DOCUMENTS)
                .get();

        QuerySnapshot snapshot = query.get();
        if (snapshot.isEmpty()) return List.of();

        return snapshot.getDocuments()
                .stream()
                .map(doc -> doc.toObject(Agente.class))
                .toList();
    }

    @Override
    public List<Agente> obterTodasFichas() throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = getCollection().get();

        QuerySnapshot snapshot = query.get();
        if (snapshot.isEmpty()) return List.of();

        return snapshot
                .getDocuments()
                .stream().map(doc -> doc.toObject(Agente.class))
                .toList();
    }

    @Override
    public Optional<Agente> obterPorId(String idFicha) throws ExecutionException, InterruptedException {
        DocumentSnapshot doc = getFichasCollection(idFicha).get().get();

        if (!doc.exists()) return Optional.empty();
        return Optional.ofNullable(doc.toObject(Agente.class));
    }

    @Override
    public Agente persistirFicha(Agente agente) throws ExecutionException, InterruptedException {
        DocumentReference doc = getCollection().add(agente).get();
        return doc.get().get().toObject(Agente.class);
    }

    @Override
    public void alterarFicha(String idFicha, Map<String, Object> campos) throws ExecutionException, InterruptedException {
        getFichasCollection(idFicha).update(campos).get();
    }

    @Override
    public void deletarFicha(String idFicha) {
        getFichasCollection(idFicha).delete();
    }

    // =============================== HABILIDADES ===============================
    @Override
    public List<Habilidade> obterHabilidades(String idFicha) throws ExecutionException, InterruptedException {
        QuerySnapshot query = getHabilidadesCollection(idFicha).get().get();

        return query.getDocuments()
                .stream().map(doc -> doc.toObject(Habilidade.class))
                .toList();
    }

    @Override
    public Habilidade persistirHabilidade(String idFicha, Habilidade habilidade) throws ExecutionException, InterruptedException {
        DocumentReference doc = getHabilidadesCollection(idFicha)
                .add(habilidade).get();

        return doc.get().get().toObject(Habilidade.class);
    }

    @Override
    public void atualizarHabilidade(String idFicha, String idHabilidade, Habilidade habilidade) throws ExecutionException, InterruptedException {
        getHabilidadesCollection(idFicha)
                .document(idHabilidade).set(habilidade).get();
    }

    @Override
    public void deletarHabilidade(String idFicha, String idHabilidade) {
        getHabilidadesCollection(idFicha)
                .document(idHabilidade).delete();
    }

    @Override
    public boolean existeDocHabilidade(String idFicha, String idHabilidade) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentSnapshot> future = getHabilidadesCollection(idFicha)
                .document(idHabilidade).get();

        return future.get().exists();
    }

}
