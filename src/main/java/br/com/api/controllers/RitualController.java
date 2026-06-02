package br.com.api.controllers;

import br.com.api.domain.dtos.ritual.RitualRequestDTO;
import br.com.api.domain.dtos.ritual.RitualResponseDTO;
import br.com.api.services.RitualServiceImpl;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static br.com.api.infra.security.AuthUtil.extractBearerToken;

@Path("/api/v1/agentes/{idFicha}/rituais")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class RitualController {
    @Inject
    RitualServiceImpl service;

    @GET
    public Response getRituals(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idFicha") String idFicha) throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        List<RitualResponseDTO> todasRituals = service.obterTudo(token, idFicha);

        return Response.ok(todasRituals).build();
    }

    @POST
    public Response postRitual(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idFicha") String idFicha,
            @Valid RitualRequestDTO request) throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        RitualResponseDTO criar = service.adicionar(token, idFicha, request);

        return Response
                .status(Response.Status.CREATED)
                .entity(criar)
                .build();
    }

    @PUT
    @Path("/{idRitual}")
    public Response putRitual(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idFicha") String idFicha,
            @PathParam("idRitual") String idRitual,
            @Valid RitualRequestDTO request
    ) throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        service.atualizar(token, idFicha, idRitual, request);

        return Response.ok().build();
    }

    @DELETE
    @Path("/{idRitual}")
    public Response deleteRitual (
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idFicha") String idFicha,
            @PathParam("idRitual") String idRitual
    ) throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        service.deletar(token, idFicha, idRitual);

        return Response.noContent().build();
    }
}
