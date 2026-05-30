package br.com.api.controllers;

import br.com.api.domain.dtos.agente.AgenteCreateDTO;
import br.com.api.domain.dtos.agente.AgenteUpdateDTO;
import br.com.api.services.interfaces.AgenteService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.concurrent.ExecutionException;

@Path("/api/v1/ficha/agente")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AgenteController {

    @Inject
    AgenteService service;

    // HELPER
    private String tokenTrim(String authHeader) {
        return authHeader.replace("Bearer", "").trim();
    }

    @GET
    @Path("/{idFicha}")
    public Response get(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idFicha") String idFicha) throws ExecutionException, InterruptedException {
        return Response
                .ok(service.obter(tokenTrim(authHeader), idFicha))
                .build();
    }

    @POST
    public Response post(
            @HeaderParam("Authorization") String authHeader,
            @Valid AgenteCreateDTO request) throws ExecutionException, InterruptedException {
        return Response
                .status(Response.Status.CREATED)
                .entity(service.criar(tokenTrim(authHeader), request))
                .build();
    }

    @PATCH
    @Path("/{idFicha}")
    public Response patch (
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idFicha") String idFicha,
            @Valid AgenteUpdateDTO request
    ) throws ExecutionException, InterruptedException {

        service.atualizar(tokenTrim(authHeader), idFicha, request);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{idFicha}")
    public Response delete (
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idFicha") String idFicha
    ) throws ExecutionException, InterruptedException {

        service.deletar(tokenTrim(authHeader), idFicha);
        return Response.noContent().build();
    }
}
