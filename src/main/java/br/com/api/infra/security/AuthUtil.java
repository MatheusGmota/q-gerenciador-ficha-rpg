package br.com.api.infra.security;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class AuthUtil {
    public static String extractBearerToken(String authHeader) {

        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            throw new WebApplicationException(
                    "Token inválido",
                    Response.Status.UNAUTHORIZED
            );
        }

        return authHeader.substring(7).trim();
    }
}
