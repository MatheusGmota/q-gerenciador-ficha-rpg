package br.com.api.repositories;

import br.com.api.domain.entities.subcollections.Item;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemRepositoryImpl extends FirestoreGenericRepository<Item> {
    @Override
    protected Class<Item> getEntityClass() {
        return Item.class;
    }

}
