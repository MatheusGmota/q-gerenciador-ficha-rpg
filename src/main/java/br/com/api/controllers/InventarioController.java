package br.com.api.controllers;

import br.com.api.domain.dtos.inventario.InventarioResponseDTO;
import br.com.api.domain.dtos.inventario.InventarioUpdateDTO;
import br.com.api.domain.dtos.inventario.ItemRequestDTO;
import br.com.api.domain.dtos.inventario.ItemResponseDTO;
import br.com.api.services.interfaces.InventarioService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.concurrent.ExecutionException;

import static br.com.api.infra.security.AuthUtil.extractBearerToken;

@Path("/api/v1/agentes/{idFicha}/inventarios")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class InventarioController {

    @Inject
    InventarioService service;

    @GET
    public Response getById(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idFicha") String idFicha
    ) throws ExecutionException, InterruptedException {

        String token = extractBearerToken(authHeader);
        InventarioResponseDTO response = service.obterOuCriar(token, idFicha);

        return Response.ok(response).build();
    }

    @PATCH
    public Response patch (
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idFicha") String idFicha,
            @Valid InventarioUpdateDTO request
    ) throws ExecutionException, InterruptedException {

        String token = extractBearerToken(authHeader);
        service.atualizar(token, idFicha, request);

        return Response.ok().build();
    }

    // ========== ITENS ==========
    @GET
    @Path("/{idItem}")
    public Response getItem(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idFicha") String idFicha,
            @PathParam("idItem") String idItem
    ) throws ExecutionException, InterruptedException {

        String token = extractBearerToken(authHeader);
        ItemResponseDTO response = service.obterItemPorId(token, idFicha, idItem);

        return Response
                .ok(response)
                .build();
    }

    @POST
    public Response postItem(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idFicha") String idFicha,
            @Valid ItemRequestDTO request) throws ExecutionException, InterruptedException {

        String token = extractBearerToken(authHeader);
        ItemResponseDTO response = service.adicionarItem(token, idFicha, request);

        return Response
                .status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    @PATCH
    @Path("/{idItem}")
    public Response patchItem(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idFicha") String idFicha,
            @PathParam("idItem") String idItem,
            @Valid ItemRequestDTO request
    ) throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        service.atualizarItem(token, idFicha, idItem, request);

        return Response.ok().build();
    }

    @DELETE
    @Path("/{idItem}")
    public Response deleteItem (
            @HeaderParam("Authorization") String authHeader,
            @PathParam("idFicha") String idFicha,
            @PathParam("idItem") String idItem
    ) throws ExecutionException, InterruptedException {
        String token = extractBearerToken(authHeader);
        service.deletarItem(token, idFicha, idItem);

        return Response.noContent().build();
    }
}
