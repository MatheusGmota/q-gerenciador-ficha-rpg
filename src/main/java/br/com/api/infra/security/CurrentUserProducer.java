package br.com.api.infra.security;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

/**
 * Permite `@Inject FirebaseUserPrincipal currentUser;` em qualquer service/validator
 * request-scoped, sem precisar receber token como parâmetro.
 *
 * Só deve ser injetado em pontos alcançáveis por rotas protegidas
 * (@Authenticated / @RolesAllowed) — nessas rotas a SecurityIdentity nunca é anônima,
 * pois o Quarkus já barra a request com 401 antes de chegar ao método.
 */
@RequestScoped
public class CurrentUserProducer {

    @Inject
    SecurityIdentity identity;

    @Produces
    @RequestScoped
    public FirebaseUserPrincipal currentUser() {
        if (identity.isAnonymous()) {
            throw new WebApplicationException("Usuário não autenticado", Response.Status.UNAUTHORIZED);
        }
        return (FirebaseUserPrincipal) identity.getPrincipal();
    }
}
