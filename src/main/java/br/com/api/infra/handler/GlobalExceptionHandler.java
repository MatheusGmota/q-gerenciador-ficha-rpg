package br.com.api.infra.handler;

import com.google.firebase.auth.FirebaseAuthException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable ex) {

        if (ex instanceof NotFoundException nfe) {
            log.error("NotFoundException: {}", ex.getMessage(),ex);
            return build(Response.Status.NOT_FOUND, nfe.getLocalizedMessage());
        }

        if (ex instanceof IllegalArgumentException ia) {
            log.error("IllegalArgumentException: {}", ex.getMessage(),ex);
            return build(Response.Status.BAD_REQUEST, ia.getLocalizedMessage());
        }

        if (ex instanceof ExecutionException exc) {
            log.error("ExecutionException: {}", ex.getMessage(),ex);
            return build(Response.Status.INTERNAL_SERVER_ERROR, exc.getLocalizedMessage());
        }

        if (ex instanceof InterruptedException ie) {
            log.error("InterruptedException: {}", ex.getMessage(),ex);
            return build(Response.Status.INTERNAL_SERVER_ERROR, ie.getLocalizedMessage());
        }

        if (ex instanceof FirebaseAuthException fe) {
            log.error("FirebaseAuthException: {}", ex.getMessage(),ex);
            return switch (fe.getAuthErrorCode().name()) {
                case "EMAIL_ALREADY_EXISTS" -> build(Response.Status.CONFLICT, fe.getLocalizedMessage());
                case "INVALID_EMAIL" -> build(Response.Status.BAD_REQUEST, "E-mail inválido");
                case "WEAK_PASSWORD" -> build(Response.Status.BAD_REQUEST, "Senha fraca. Use ao menos 6 caracteres");
                default ->
                        build(Response.Status.INTERNAL_SERVER_ERROR, "Erro ao criar usuário: " + fe.getLocalizedMessage() + fe.getAuthErrorCode());
            };
        }

        if (ex instanceof ConstraintViolationException cve) {
            log.error("ConstraintViolationException: {}", ex.getMessage(),ex);
            Map<String, String> erros = cve.getConstraintViolations().stream()
                    .collect(Collectors.toMap(
                            v -> {
                                String path = v.getPropertyPath().toString();
                                String[] parts = path.split("\\.");
                                return parts[parts.length - 1];
                            },
                            ConstraintViolation::getMessage,
                            (a, b) -> a
                    ));
            return build(Response.Status.BAD_REQUEST, "Erro de validação", erros);
        }

        if (ex instanceof WebApplicationException wae) {
            log.error("WebApplicationError: {}", ex.getMessage(),ex);
            return build(
                    Response.Status.fromStatusCode(wae.getResponse().getStatus()),
                    wae.getMessage()
            );
        }

        log.error("Erro interno não tratado: {}", ex.getMessage(), ex);
        return build(Response.Status.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro interno. Tente novamente mais tarde.");
    }

    private Response build(Response.Status status, String mensagem) {
        return build(status, mensagem, null);
    }

    private Response build(Response.Status status, String mensagem, Object detalhes) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.getStatusCode());
        body.put("error", mensagem);
        if (detalhes != null) body.put("details", detalhes);
        return Response.status(status).entity(body).build();
    }
}
