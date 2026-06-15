package br.com.api.controllers;

import br.com.api.domain.dtos.ameaca.AmeacaResponseDTO;
import br.com.api.domain.dtos.ameaca.AmeacaResumoResponseDTO;
import br.com.api.domain.dtos.ameaca.AmeacaUpdateDTO;
import br.com.api.domain.dtos.pericias.PericiaUpdateDTO;
import br.com.api.services.interfaces.AmeacaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static br.com.api.infra.security.AuthUtil.extractBearerToken;

@Path("/api/v1/ameacas")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AmeacaController {

    @Inject
    AmeacaService service;

    @GET
    @Path("/{idFicha}")
    public Response getById(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idFicha") String idFicha) throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        AmeacaResponseDTO obter = service.obter(token, idFicha);

        return Response.ok(obter).build();
    }

    @GET
    @Path("/usuario")
    public Response getAllByUserId(
            @HeaderParam("Authorization") String authHeader
    ) throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        List<AmeacaResumoResponseDTO> obter = service.obterPorIdUsuario(token);

        return Response.ok(obter).build();
    }

    @GET
    public Response getAll(
            @HeaderParam("Authorization") String authHeader) throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        List<AmeacaResumoResponseDTO> obter = service.obterTudo(token);

        return Response.ok(obter).build();
    }

    @POST
    public Response post(@HeaderParam("Authorization") String authHeader) throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        AmeacaResponseDTO criar = service.criar(token);

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
            @Valid AmeacaUpdateDTO request
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

    @PUT
    @Path("/{idFicha}/pericias")
    public Response putPericias(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idFicha") String idFicha,
            @Valid PericiaUpdateDTO request
    ) throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);

        service.atualizarPericia(token, idFicha, request);
        return Response
                .noContent()
                .build();
    }
}
