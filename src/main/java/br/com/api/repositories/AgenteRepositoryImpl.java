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

    @Inject
    public Firestore db;

    private CollectionReference getCollection() {
        return db.collection(COLLECTION_NAME);
    }

    private DocumentReference getFichasCollection(String idFicha) {
        return db.collection(COLLECTION_NAME).document(idFicha);
    }

    @Override
    public Optional<Agente> obterPorId(String idFicha) throws ExecutionException, InterruptedException {
        DocumentSnapshot doc = getFichasCollection(idFicha)
                .get()
                .get();

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

    @Override
    public List<Habilidade> obterHabilidades(String idFicha) throws ExecutionException, InterruptedException {
        QuerySnapshot query = getFichasCollection(idFicha).collection(Habilidade.COLLECTION_NAME).get().get();

        return query.getDocuments()
                .stream().map(doc -> doc.toObject(Habilidade.class))
                .toList();
    }

    @Override
    public Habilidade persistirHabilidade(String idFicha, Habilidade habilidade) throws ExecutionException, InterruptedException {
        DocumentReference doc = getFichasCollection(idFicha).collection(Habilidade.COLLECTION_NAME)
                .add(habilidade).get();

        return doc.get().get().toObject(Habilidade.class);
    }

    @Override
    public void atualizarHabilidade(String idFicha, String idHabilidade, Habilidade habilidade) throws ExecutionException, InterruptedException {
        getFichasCollection(idFicha).collection(Habilidade.COLLECTION_NAME)
                .document(idHabilidade).set(habilidade).get();
    }

    @Override
    public void deletarHabilidade(String idFicha, String idHabilidade) {
        getFichasCollection(idFicha).collection(Habilidade.COLLECTION_NAME)
                .document(idHabilidade).delete();
    }

    @Override
    public boolean existeDocHabilidade(String idFicha, String idHabilidade) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentSnapshot> future = getFichasCollection(idFicha).collection(Habilidade.COLLECTION_NAME)
                .document(idHabilidade).get();

        return future.get().exists();
    }

}
