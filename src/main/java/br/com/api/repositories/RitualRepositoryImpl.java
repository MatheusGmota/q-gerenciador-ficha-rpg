package br.com.api.repositories;

import br.com.api.domain.entities.subcollections.Ritual;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RitualRepositoryImpl extends FirestoreGenericRepository<Ritual> {
    @Override
    protected Class<Ritual> getEntityClass() {
        return Ritual.class;
    }
}
