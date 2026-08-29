package br.com.api.controllers;

import br.com.api.domain.dtos.campanha.CampanhaCreateDTO;
import br.com.api.domain.dtos.campanha.CampanhaResponseDTO;
import br.com.api.domain.dtos.campanha.CampanhaResumoResponseDTO;
import br.com.api.domain.dtos.campanha.CampanhaUpdateDTO;
import br.com.api.domain.dtos.convite.ConviteCreateDTO;
import br.com.api.domain.dtos.convite.ConviteResponseDTO;
import br.com.api.domain.dtos.membro.MembroResponseDTO;
import br.com.api.services.interfaces.CampanhaService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Path("/api/v1/campanhas")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class CampanhaController {

    @Inject
    private CampanhaService service;

    @GET
    public Response obterCampanhasUsuario() throws ExecutionException, InterruptedException {
        List<CampanhaResumoResponseDTO> response = service.obterPorIdUsuario();
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public Response obterCampanha(
            @PathParam("id") String id)
            throws ExecutionException, InterruptedException {
        CampanhaResponseDTO response = service.obter(id);

        return Response.ok(response).build();
    }

    @POST
    public Response cadastrarCampanha(
            @Valid CampanhaCreateDTO request)
            throws ExecutionException, InterruptedException {
        CampanhaResponseDTO response = service.criar(request);

        return Response
                .status(201)
                .entity(response)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizarCampanha(
            @PathParam("id") String id,
            @Valid CampanhaUpdateDTO request)
            throws ExecutionException, InterruptedException {
        service.atualizar(id, request);

        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletarCampanha(
            @PathParam("id") String id)
            throws ExecutionException, InterruptedException {
        service.deletar(id);

        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/membros")
    public Response obterMembros(
            @PathParam("id") String id)
            throws ExecutionException, InterruptedException {
        List<MembroResponseDTO> response = service.obterMembros(id);

        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}/membros/{idUsuario}")
    public Response removerMembro(
            @PathParam("id") String id,
            @PathParam("idUsuario") String idUsuario)
            throws ExecutionException, InterruptedException {
        service.removerMembro(id, idUsuario);

        return Response.noContent().build();
    }

    @POST
    @Path("/{id}/convites")
    public Response gerarConvite(
            @PathParam("id") String id,
            @Valid ConviteCreateDTO request)
            throws ExecutionException, InterruptedException {
        ConviteResponseDTO response = service.gerarConvite(id, request);

        return Response.status(201).entity(response).build();
    }
}