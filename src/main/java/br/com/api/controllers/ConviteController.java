package br.com.api.controllers;

import br.com.api.domain.dtos.membro.MembroResponseDTO;
import br.com.api.services.interfaces.CampanhaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.concurrent.ExecutionException;

import static br.com.api.infra.security.AuthUtil.extractBearerToken;

@Path("/api/v1/convites")
@Produces({MediaType.APPLICATION_JSON})
public class ConviteController {

    @Inject
    private CampanhaService service;

    @POST
    @Path("/{token}/entrar")
    public Response entrar(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("token") String token)
            throws ExecutionException, InterruptedException {

        String authToken = extractBearerToken(authHeader);
        MembroResponseDTO response = service.entrarPorConvite(authToken, token);

        return Response.status(201).entity(response).build();
    }
}