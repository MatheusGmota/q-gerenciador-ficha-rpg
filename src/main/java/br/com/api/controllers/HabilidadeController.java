package br.com.api.controllers;

import br.com.api.domain.dtos.habilidade.HabilidadeRequestDTO;
import br.com.api.domain.dtos.habilidade.HabilidadeResponseDTO;
import br.com.api.services.interfaces.HabilidadeService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Path("/api/v1/agentes/{idFicha}/habilidades")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class HabilidadeController {

    @Inject
    HabilidadeService service;

    @GET
    public Response getHabilidades(
            @PathParam("idFicha") String idFicha) throws ExecutionException, InterruptedException {

        List<HabilidadeResponseDTO> todasHabilidades = service.obterTudo(idFicha);

        return Response.ok(todasHabilidades).build();
    }

    @POST
    public Response postHabilidade(
            @PathParam("idFicha") String idFicha,
            @Valid HabilidadeRequestDTO request) throws ExecutionException, InterruptedException {

        HabilidadeResponseDTO criar = service.adicionar(idFicha, request);

        return Response
                .status(Response.Status.CREATED)
                .entity(criar)
                .build();
    }

    @PUT
    @Path("/{idHabilidade}")
    public Response putHabilidade(
            @PathParam("idFicha") String idFicha,
            @PathParam("idHabilidade") String idHabilidade,
            @Valid HabilidadeRequestDTO request
    ) throws ExecutionException, InterruptedException {

        service.atualizar(idFicha, idHabilidade, request);

        return Response.ok().build();
    }

    @DELETE
    @Path("/{idHabilidade}")
    public Response deleteHabilidade (
            @PathParam("idFicha") String idFicha,
            @PathParam("idHabilidade") String idHabilidade
    ) throws ExecutionException, InterruptedException {

        service.deletar(idFicha, idHabilidade);

        return Response.noContent().build();
    }
}
