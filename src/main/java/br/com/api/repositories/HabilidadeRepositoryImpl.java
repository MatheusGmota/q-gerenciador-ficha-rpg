package br.com.api.repositories;

import br.com.api.domain.entities.subcollections.Habilidade;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HabilidadeRepositoryImpl extends FirestoreGenericRepository<Habilidade> {
    @Override
    protected Class<Habilidade> getEntityClass() {
        return Habilidade.class;
    }
}
