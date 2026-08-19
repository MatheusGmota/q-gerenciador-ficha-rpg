package br.com.api.controllers;

import br.com.api.domain.dtos.campanha.CampanhaCreateDTO;
import br.com.api.domain.dtos.campanha.CampanhaResponseDTO;
import br.com.api.domain.dtos.campanha.CampanhaResumoResponseDTO;
import br.com.api.domain.dtos.campanha.CampanhaUpdateDTO;
import br.com.api.domain.dtos.convite.ConviteCreateDTO;
import br.com.api.domain.dtos.convite.ConviteResponseDTO;
import br.com.api.domain.dtos.membro.MembroResponseDTO;
import br.com.api.services.interfaces.CampanhaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static br.com.api.infra.security.AuthUtil.extractBearerToken;

@Path("/api/v1/campanhas")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class CampanhaController {

    @Inject
    private CampanhaService service;

    @GET
    public Response obterCampanhasUsuario(@HeaderParam("Authorization") String authHeader) throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        List<CampanhaResumoResponseDTO> response = service.obterPorIdUsuario(token);
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public Response obterCampanha(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("id") String id)
            throws ExecutionException, InterruptedException {

        String token = extractBearerToken(authHeader);
        CampanhaResponseDTO response = service.obter(token, id);

        return Response.ok(response).build();
    }

    @POST
    public Response cadastrarCampanha(
            @HeaderParam("Authorization") String authHeader,
            @Valid CampanhaCreateDTO request)
            throws ExecutionException, InterruptedException {

        String token = extractBearerToken(authHeader);
        CampanhaResponseDTO response = service.criar(token, request);

        return Response
                .status(201)
                .entity(response)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizarCampanha(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("id") String id,
            @Valid CampanhaUpdateDTO request)
            throws ExecutionException, InterruptedException {

        String token = extractBearerToken(authHeader);
        service.atualizar(token, id, request);

        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletarCampanha(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("id") String id)
            throws ExecutionException, InterruptedException {

        String token = extractBearerToken(authHeader);
        service.deletar(token, id);

        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/membros")
    public Response obterMembros(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("id") String id)
            throws ExecutionException, InterruptedException {

        String token = extractBearerToken(authHeader);
        List<MembroResponseDTO> response = service.obterMembros(token, id);

        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}/membros/{idUsuario}")
    public Response removerMembro(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("id") String id,
            @PathParam("idUsuario") String idUsuario)
            throws ExecutionException, InterruptedException {

        String token = extractBearerToken(authHeader);
        service.removerMembro(token, id, idUsuario);

        return Response.noContent().build();
    }

    @POST
    @Path("/{id}/convites")
    public Response gerarConvite(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("id") String id,
            @Valid ConviteCreateDTO request)
            throws ExecutionException, InterruptedException {

        String token = extractBearerToken(authHeader);
        ConviteResponseDTO response = service.gerarConvite(token, id, request);

        return Response.status(201).entity(response).build();
    }
}