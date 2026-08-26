package br.com.api.controllers;

import br.com.api.domain.dtos.agente.AgenteResponseDTO;
import br.com.api.domain.dtos.agente.AgenteUpdateDTO;
import br.com.api.domain.dtos.ameaca.AmeacaResponseDTO;
import br.com.api.domain.dtos.ameaca.AmeacaUpdateDTO;
import br.com.api.domain.dtos.fichavinculada.FichaVinculadaResponseDTO;
import br.com.api.services.interfaces.FichaCampanhaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static br.com.api.infra.security.AuthUtil.extractBearerToken;

@Path("/api/v1/campanhas/{idCampanha}")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class FichaCampanhaController {

    @Inject
    FichaCampanhaService service;

    @GET
    @Path("/fichas")
    public Response listarFichas(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idCampanha") String idCampanha)
            throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        List<FichaVinculadaResponseDTO> response = service.listarFichas(token, idCampanha);
        return Response.ok(response).build();
    }

    // ================ AGENTES ====================

    @POST
    @Path("/agentes/{idFicha}")
    public Response vincularAgente(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idCampanha") String idCampanha,
            @PathParam("idFicha") String idFicha)
            throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        FichaVinculadaResponseDTO response = service.vincularAgente(token, idCampanha, idFicha);
        return Response.status(201).entity(response).build();
    }

    @DELETE
    @Path("/agentes/{idFicha}")
    public Response desvincularAgente(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idCampanha") String idCampanha,
            @PathParam("idFicha") String idFicha)
            throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        service.desvincularAgente(token, idCampanha, idFicha);
        return Response.noContent().build();
    }

    @GET
    @Path("/agentes/{idFicha}")
    public Response obterAgente(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idCampanha") String idCampanha,
            @PathParam("idFicha") String idFicha)
            throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        AgenteResponseDTO response = service.obterAgente(token, idCampanha, idFicha);
        return Response.ok(response).build();
    }

    @PATCH
    @Path("/agentes/{idFicha}")
    public Response atualizarAgente(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idCampanha") String idCampanha,
            @PathParam("idFicha") String idFicha,
            @Valid AgenteUpdateDTO request)
            throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        service.atualizarAgente(token, idCampanha, idFicha, request);
        return Response.ok().build();
    }

    // ================ AMEAÇAS ====================

    @POST
    @Path("/ameacas/{idFicha}")
    public Response vincularAmeaca(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idCampanha") String idCampanha,
            @PathParam("idFicha") String idFicha)
            throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        FichaVinculadaResponseDTO response = service.vincularAmeaca(token, idCampanha, idFicha);
        return Response.status(201).entity(response).build();
    }

    @DELETE
    @Path("/ameacas/{idFicha}")
    public Response desvincularAmeaca(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idCampanha") String idCampanha,
            @PathParam("idFicha") String idFicha)
            throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        service.desvincularAmeaca(token, idCampanha, idFicha);
        return Response.noContent().build();
    }

    @GET
    @Path("/ameacas/{idFicha}")
    public Response obterAmeaca(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idCampanha") String idCampanha,
            @PathParam("idFicha") String idFicha)
            throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        AmeacaResponseDTO response = service.obterAmeaca(token, idCampanha, idFicha);
        return Response.ok(response).build();
    }

    @PATCH
    @Path("/ameacas/{idFicha}")
    public Response atualizarAmeaca(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idCampanha") String idCampanha,
            @PathParam("idFicha") String idFicha,
            @Valid AmeacaUpdateDTO request)
            throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        service.atualizarAmeaca(token, idCampanha, idFicha, request);
        return Response.ok().build();
    }
}