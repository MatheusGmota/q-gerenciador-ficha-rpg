package br.com.api.controllers;

import br.com.api.domain.dtos.agente.AgenteResponseDTO;
import br.com.api.domain.dtos.agente.AgenteUpdateDTO;
import br.com.api.domain.dtos.ameaca.AmeacaResponseDTO;
import br.com.api.domain.dtos.ameaca.AmeacaUpdateDTO;
import br.com.api.domain.dtos.fichavinculada.FichaVinculadaResponseDTO;
import br.com.api.services.interfaces.FichaCampanhaService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Path("/api/v1/campanha")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class FichaCampanhaController {

    @Inject
    FichaCampanhaService service;

    @GET
    @Path("/{id}/fichas")
    public Response listarFichas(
            @PathParam("id") String id)
            throws ExecutionException, InterruptedException {
        List<FichaVinculadaResponseDTO> response = service.listarFichas(id);
        return Response.ok(response).build();
    }

    // ================ AGENTES ====================

    @POST
    @Path("/{id}/agentes/{idFicha}")
    public Response vincularAgente(
            @PathParam("id") String id,
            @PathParam("idFicha") String idFicha)
            throws ExecutionException, InterruptedException {
        FichaVinculadaResponseDTO response = service.vincularAgente(id, idFicha);
        return Response.status(201).entity(response).build();
    }

    @DELETE
    @Path("/{id}/agentes/{idFicha}")
    public Response desvincularAgente(
            @PathParam("id") String id,
            @PathParam("idFicha") String idFicha)
            throws ExecutionException, InterruptedException {
        service.desvincularAgente(id, idFicha);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/agentes/{idFicha}")
    public Response obterAgente(
            @PathParam("id") String id,
            @PathParam("idFicha") String idFicha)
            throws ExecutionException, InterruptedException {
        AgenteResponseDTO response = service.obterAgente(id, idFicha);
        return Response.ok(response).build();
    }

    @PATCH
    @Path("/{id}/agentes/{idFicha}")
    public Response atualizarAgente(
            @PathParam("id") String id,
            @PathParam("idFicha") String idFicha,
            @Valid AgenteUpdateDTO request)
            throws ExecutionException, InterruptedException {
        service.atualizarAgente(id, idFicha, request);
        return Response.ok().build();
    }

    // ================ AMEAÇAS ====================

    @POST
    @Path("/{id}/ameacas/{idFicha}")
    public Response vincularAmeaca(
            @PathParam("id") String id,
            @PathParam("idFicha") String idFicha)
            throws ExecutionException, InterruptedException {
        FichaVinculadaResponseDTO response = service.vincularAmeaca(id, idFicha);
        return Response.status(201).entity(response).build();
    }

    @DELETE
    @Path("/{id}/ameacas/{idFicha}")
    public Response desvincularAmeaca(
            @PathParam("id") String id,
            @PathParam("idFicha") String idFicha)
            throws ExecutionException, InterruptedException {
        service.desvincularAmeaca(id, idFicha);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/ameacas/{idFicha}")
    public Response obterAmeaca(
            @PathParam("id") String id,
            @PathParam("idFicha") String idFicha)
            throws ExecutionException, InterruptedException {
        AmeacaResponseDTO response = service.obterAmeaca(id, idFicha);
        return Response.ok(response).build();
    }

    @PATCH
    @Path("/{id}/ameacas/{idFicha}")
    public Response atualizarAmeaca(
            @PathParam("id") String id,
            @PathParam("idFicha") String idFicha,
            @Valid AmeacaUpdateDTO request)
            throws ExecutionException, InterruptedException {
        service.atualizarAmeaca(id, idFicha, request);
        return Response.ok().build();
    }
}