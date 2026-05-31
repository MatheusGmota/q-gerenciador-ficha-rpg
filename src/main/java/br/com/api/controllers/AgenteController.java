package br.com.api.controllers;

import br.com.api.domain.dtos.agente.AgenteCreateDTO;
import br.com.api.domain.dtos.agente.AgenteResponseDTO;
import br.com.api.domain.dtos.agente.AgenteResumoResponseDTO;
import br.com.api.domain.dtos.agente.AgenteUpdateDTO;
import br.com.api.domain.dtos.habilidade.HabilidadeRequestDTO;
import br.com.api.domain.dtos.habilidade.HabilidadeResponseDTO;
import br.com.api.services.interfaces.AgenteService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static br.com.api.infra.security.AuthUtil.extractBearerToken;

@Path("/api/v1/agentes")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AgenteController {

    @Inject
    AgenteService service;

    @GET
    @Path("/{idFicha}")
    public Response getById(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idFicha") String idFicha) throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        AgenteResponseDTO obter = service.obter(token, idFicha);

        return Response.ok(obter).build();
    }

    @GET
    @Path("/usuario")
    public Response getAllByUserId(
            @HeaderParam("Authorization") String authHeader
    ) throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        List<AgenteResumoResponseDTO> obter = service.obterPorIdUsuario(token);

        return Response.ok(obter).build();
    }

    @GET
    public Response getAll(
            @HeaderParam("Authorization") String authHeader) throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        List<AgenteResumoResponseDTO> obter = service.obterTudo(token);

        return Response.ok(obter).build();
    }

    @POST
    public Response post(
            @HeaderParam("Authorization") String authHeader,
            @Valid AgenteCreateDTO request) throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        AgenteResponseDTO criar = service.criar(token, request);

        return Response
                .status(Response.Status.CREATED)
                .entity(criar)
                .build();
    }

    @PATCH
    @Path("/{idFicha}")
    public Response patch (
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idFicha") String idFicha,
            @Valid AgenteUpdateDTO request
    ) throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        service.atualizar(token, idFicha, request);

        return Response.ok().build();
    }

    @DELETE
    @Path("/{idFicha}")
    public Response delete (
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idFicha") String idFicha
    ) throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        service.deletar(token, idFicha);

        return Response.noContent().build();
    }

    // =============================== HABILIDADES ===============================

    @GET
    @Path("/{idFicha}/habilidades")
    public Response getHabilidades(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idFicha") String idFicha) throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        List<HabilidadeResponseDTO> todasHabilidades = service.obterTodasHabilidades(token, idFicha);

        return Response.ok(todasHabilidades).build();
    }

    @POST
    @Path("/{idFicha}/habilidades")
    public Response postHabilidade(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idFicha") String idFicha,
            @Valid HabilidadeRequestDTO request) throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        HabilidadeResponseDTO criar = service.adicionarHabilidade(token, idFicha, request);

        return Response
                .status(Response.Status.CREATED)
                .entity(criar)
                .build();
    }

    @PUT
    @Path("/{idFicha}/habilidades/{idHabilidade}")
    public Response putHabilidade(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idFicha") String idFicha,
            @PathParam("idHabilidade") String idHabilidade,
            @Valid HabilidadeRequestDTO request
    ) throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        service.atualizarHabilidade(token, idFicha, idHabilidade, request);

        return Response.ok().build();
    }

    @DELETE
    @Path("/{idFicha}/habilidades/{idHabilidade}")
    public Response deleteHabilidade (
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idFicha") String idFicha,
            @PathParam("idHabilidade") String idHabilidade
    ) throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        service.deletarHabilidade(token, idFicha, idHabilidade);

        return Response.noContent().build();
    }
}
