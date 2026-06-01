package br.com.api.repositories;

import br.com.api.annotation.FirestoreCollection;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public abstract class FirestoreGenericRepository<T> {

    @Inject
    protected Firestore db;

    protected abstract Class<T> getEntityClass();

    protected String getCollectionName() {
        return getEntityClass()
                .getAnnotation(FirestoreCollection.class)
                .value();
    }

    protected CollectionReference getSubCollection(String idFicha) {
        return db.collection("fichas")
                .document(idFicha)
                .collection(getCollectionName());
    }

    public List<T> procurarTudo(String idFicha) throws ExecutionException, InterruptedException {
        QuerySnapshot query = getSubCollection(idFicha).get().get();

        return query.getDocuments()
                .stream().map(doc -> doc.toObject(getEntityClass()))
                .toList();
    }

    public Optional<T> procurarPorId(String idFicha, String id) throws ExecutionException, InterruptedException {
        DocumentSnapshot doc = getSubCollection(idFicha).document(id).get().get();

        if (!doc.exists()) return Optional.empty();
        return Optional.ofNullable(doc.toObject(getEntityClass()));
    }


    public T persistir(String idFicha, T entity) throws ExecutionException, InterruptedException {
        DocumentReference doc = getSubCollection(idFicha)
                .add(entity).get();

        return doc.get().get().toObject(getEntityClass());
    }


    public void editar(String idFicha, String id, T entity) throws ExecutionException, InterruptedException {
        getSubCollection(idFicha).document(id).set(entity).get();
    }


    public void remover(String idFicha, String id) {
        getSubCollection(idFicha)
                .document(id).delete();
    }


    public boolean existeDocumento(String idFicha, String id) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentSnapshot> future = getSubCollection(idFicha)
                .document(id).get();

        return future.get().exists();
    }
}
