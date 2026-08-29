package br.com.api.controllers;

import br.com.api.domain.dtos.membro.MembroResponseDTO;
import br.com.api.services.interfaces.CampanhaService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.concurrent.ExecutionException;

@Path("/api/v1/convites")
@Produces({MediaType.APPLICATION_JSON})
@Authenticated
public class ConviteController {

    @Inject
    private CampanhaService service;

    @POST
    @Path("/{token}/entrar")
    public Response entrar(@PathParam("token") String token) throws ExecutionException, InterruptedException {
        MembroResponseDTO response = service.entrarPorConvite(token);

        return Response.status(201).entity(response).build();
    }
}