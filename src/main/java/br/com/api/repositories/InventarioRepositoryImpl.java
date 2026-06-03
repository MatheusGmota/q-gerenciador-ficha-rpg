package br.com.api.repositories;

import br.com.api.domain.entities.Inventario;
import br.com.api.repositories.interfaces.InventarioRepository;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
public class InventarioRepositoryImpl implements InventarioRepository {
    private static final String COLLECTION_NAME = "inventarios";

    @Inject
    public Firestore db;

    private CollectionReference getCollection() {
        return db.collection(COLLECTION_NAME);
    }

    private DocumentReference getInventarioDocument(String idFicha) {
        return getCollection().document(idFicha);
    }

    @Override
    public Inventario persistir(String idFicha, Inventario inventario) throws ExecutionException, InterruptedException {
        getInventarioDocument(idFicha).set(inventario).get();
        return obterPorId(idFicha);
    }

    @Override
    public Inventario obterPorId(String idFicha) throws ExecutionException, InterruptedException {
        DocumentSnapshot doc = getInventarioDocument(idFicha).get().get();
        return doc.toObject(Inventario.class);
    }

    @Override
    public void alterar(String idFicha, Map<String, Object> campos) throws ExecutionException, InterruptedException {
        getInventarioDocument(idFicha).update(campos).get();
    }
}
