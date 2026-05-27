package br.com.api.services;

import br.com.api.domain.dtos.user.TokenResponseDTO;
import br.com.api.domain.enums.UserRole;
import br.com.api.infra.providers.FirebaseProvider;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ApplicationScoped
public class AuthenticationService {

    private static final String SIGN_IN_URL =
            "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=";

    @ConfigProperty(name = "google.cloud.firebase.api-key")
    String firebaseApiKey;

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    @Inject
    FirebaseProvider provider;

    /**
     * Login com e-mail e senha via Firebase Identity Toolkit REST API.
     * Retorna idToken, refreshToken e dados básicos.
     */
    public TokenResponseDTO loginComEmailESenha(String email, String password, String username, UserRole userRole) throws InterruptedException {
        String body = String.format(
                "{\"email\":\"%s\",\"password\":\"%s\",\"returnSecureToken\":true}",
                email, password
        );

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(SIGN_IN_URL + firebaseApiKey))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            JsonNode json = mapper.readTree(response.body());

            if (response.statusCode() != 200) {
                String code = json.path("error").path("message").asText();
                throw new WebApplicationException(traduzirErro(code), Response.Status.UNAUTHORIZED);
            }

            return new TokenResponseDTO(
                    json.get("localId").asText(),
                    username,
                    json.get("idToken").asText(),
                    json.get("refreshToken").asText(),
                    userRole
            );

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw e;
        } catch (IOException e) {
            throw new WebApplicationException("Erro ao conectar com o Firebase",
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    // ---------- HELPERS ----------
    public FirebaseToken validarToken(String idToken) {
        try {
            return provider.getFirebaseAuth().verifyIdToken(idToken);
        } catch (FirebaseAuthException e) {
            log.error(e.getMessage());
            throw new WebApplicationException("Token inválido ou expirado",
                    Response.Status.UNAUTHORIZED);
        }
    }

    public void updateUserClaims(String uid, String username) throws FirebaseAuthException {
        // Atualiza claims se username não é nulo
        if (username != null) {
            UserRecord record = provider.getFirebaseAuth().getUser(uid);
            // Preserva claims existentes e sobrescreve apenas os alterados
            Map<String, Object> claimsAtuais = record.getCustomClaims() != null
                    ? new HashMap<>(record.getCustomClaims())
                    : new HashMap<>();
            claimsAtuais.put("username", username);

            provider.getFirebaseAuth().setCustomUserClaims(uid, claimsAtuais);
        }
    }

    public void setUserClaims(String uid, String username, UserRole role) throws FirebaseAuthException {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("admin", UserRole.ADMIN.equals(role));

        provider.getFirebaseAuth().setCustomUserClaims(uid, claims); // adiciona claims do user com username e admin(true/false)
    }

    private String traduzirErro(String code) {
        return switch (code) {
            case "EMAIL_NOT_FOUND"             -> "E-mail não cadastrado.";
            case "EMAIL_EXISTS"                -> "E-mail já cadastrado.";
            case "USER_NOT_FOUND"              -> "Usuário não cadastrado.";
            case "INVALID_PASSWORD"            -> "Senha incorreta.";
            case "USER_DISABLED"               -> "Conta desativada.";
            case "INVALID_LOGIN_CREDENTIALS"   -> "E-mail ou senha incorretos.";
            case "TOO_MANY_ATTEMPTS_TRY_LATER" -> "Muitas tentativas. Tente mais tarde.";
            case "INVALID_PHONE_NUMBER : TOO_SHORT" -> "Número de telefone é pequeno";
            default                            -> "Erro de autenticação.";
        };
    }
}
