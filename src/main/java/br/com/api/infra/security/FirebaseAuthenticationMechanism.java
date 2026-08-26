package br.com.api.infra.security;

import br.com.api.infra.providers.FirebaseProvider;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import io.quarkus.security.AuthenticationFailedException;
import io.quarkus.security.identity.IdentityProviderManager;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.request.AuthenticationRequest;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.quarkus.vertx.http.runtime.security.ChallengeData;
import io.quarkus.vertx.http.runtime.security.HttpAuthenticationMechanism;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.Collections;
import java.util.Set;

/**
 * Substitui a extração manual do header "Authorization" em cada controller.
 * Valida o idToken do Firebase e monta a SecurityIdentity uma única vez por request,
 * evitando revalidações redundantes em services/validators.
 */
@ApplicationScoped
public class FirebaseAuthenticationMechanism implements HttpAuthenticationMechanism {

    private static final String BEARER_PREFIX = "Bearer ";
    public static final String ADMIN_ROLE = "admin";
    public static final String USER_ROLE = "user";

    @Inject
    FirebaseProvider firebaseProvider;

    @Override
    public Uni<SecurityIdentity> authenticate(RoutingContext context, IdentityProviderManager identityProviderManager) {
        String authHeader = context.request().getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            // Sem token -> identidade anônima. Quem decide se a rota exige login
            // é a anotação (@Authenticated / @RolesAllowed) no controller.
            return Uni.createFrom().nullItem();
        }

        String token = authHeader.substring(BEARER_PREFIX.length()).trim();

        return Uni.createFrom().item(() -> verificarToken(token))
                // verifyIdToken é bloqueante (SDK do Firebase), então roda fora do event loop
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                .onItem().transform(this::construirIdentity)
                .onFailure().transform(AuthenticationFailedException::new);
    }

    private FirebaseToken verificarToken(String token) {
        try {
            return firebaseProvider.getFirebaseAuth().verifyIdToken(token);
        } catch (FirebaseAuthException e) {
            throw new AuthenticationFailedException(e);
        }
    }

    private SecurityIdentity construirIdentity(FirebaseToken firebaseToken) {
        QuarkusSecurityIdentity.Builder builder = QuarkusSecurityIdentity.builder();

        FirebaseUserPrincipal principal = new FirebaseUserPrincipal(firebaseToken);
        builder.setPrincipal(principal);
        builder.addRole(USER_ROLE);

        if (principal.isAdmin()) {
            builder.addRole(ADMIN_ROLE);
        }

        return builder.build();
    }

    @Override
    public Uni<ChallengeData> getChallenge(RoutingContext context) {
        ChallengeData challengeData = new ChallengeData(
                Response.Status.UNAUTHORIZED.getStatusCode(),
                "WWW-Authenticate",
                "Bearer"
        );
        return Uni.createFrom().item(challengeData);
    }

    @Override
    public Set<Class<? extends AuthenticationRequest>> getCredentialTypes() {
        return Collections.emptySet();
    }
}