package br.com.api.controllers;

import br.com.api.domain.dtos.habilidade.HabilidadeRequestDTO;
import br.com.api.domain.dtos.habilidade.HabilidadeResponseDTO;
import br.com.api.services.interfaces.HabilidadeService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static br.com.api.infra.security.AuthUtil.extractBearerToken;

@Path("/api/v1/agentes/{idFicha}/habilidades")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class HabilidadeController {

    @Inject
    HabilidadeService service;

    @GET
    public Response getHabilidades(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idFicha") String idFicha) throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        List<HabilidadeResponseDTO> todasHabilidades = service.obterTudo(token, idFicha);

        return Response.ok(todasHabilidades).build();
    }

    @POST
    public Response postHabilidade(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idFicha") String idFicha,
            @Valid HabilidadeRequestDTO request) throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        HabilidadeResponseDTO criar = service.adicionar(token, idFicha, request);

        return Response
                .status(Response.Status.CREATED)
                .entity(criar)
                .build();
    }

    @PUT
    @Path("/{idHabilidade}")
    public Response putHabilidade(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idFicha") String idFicha,
            @PathParam("idHabilidade") String idHabilidade,
            @Valid HabilidadeRequestDTO request
    ) throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        service.atualizar(token, idFicha, idHabilidade, request);

        return Response.ok().build();
    }

    @DELETE
    @Path("/{idHabilidade}")
    public Response deleteHabilidade (
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idFicha") String idFicha,
            @PathParam("idHabilidade") String idHabilidade
    ) throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        service.deletar(token, idFicha, idHabilidade);

        return Response.noContent().build();
    }
}
