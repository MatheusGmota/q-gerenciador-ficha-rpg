package br.com.api.repositories;

import br.com.api.domain.entities.Agente;
import br.com.api.repositories.interfaces.AgenteRepository;
import com.google.cloud.firestore.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

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

    @Override
    public Optional<Agente> obterPorId(String idFicha) throws ExecutionException, InterruptedException {
        DocumentSnapshot doc = getCollection().document(idFicha)
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
        getCollection().document(idFicha).update(campos).get();
    }

    @Override
    public void deletarFicha(String idFicha) {
        getCollection().document(idFicha).delete();
    }
}
