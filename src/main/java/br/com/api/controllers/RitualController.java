package br.com.api.controllers;

import br.com.api.domain.dtos.ritual.RitualRequestDTO;
import br.com.api.domain.dtos.ritual.RitualResponseDTO;
import br.com.api.services.RitualServiceImpl;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Path("/api/v1/agentes/{idFicha}/rituais")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class RitualController {

    @Inject
    RitualServiceImpl service;

    @GET
    public Response getRituals(
            @PathParam("idFicha") String idFicha) throws ExecutionException, InterruptedException {

        List<RitualResponseDTO> todasRituals = service.obterTudo(idFicha);

        return Response.ok(todasRituals).build();
    }

    @POST
    public Response postRitual(
            @PathParam("idFicha") String idFicha,
            @Valid RitualRequestDTO request) throws ExecutionException, InterruptedException {

        RitualResponseDTO criar = service.adicionar(idFicha, request);

        return Response
                .status(Response.Status.CREATED)
                .entity(criar)
                .build();
    }

    @PUT
    @Path("/{idRitual}")
    public Response putRitual(
            @PathParam("idFicha") String idFicha,
            @PathParam("idRitual") String idRitual,
            @Valid RitualRequestDTO request
    ) throws ExecutionException, InterruptedException {

        service.atualizar(idFicha, idRitual, request);

        return Response.ok().build();
    }

    @DELETE
    @Path("/{idRitual}")
    public Response deleteRitual (
            @PathParam("idFicha") String idFicha,
            @PathParam("idRitual") String idRitual
    ) throws ExecutionException, InterruptedException {

        service.deletar(idFicha, idRitual);

        return Response.noContent().build();
    }
}
