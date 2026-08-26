package br.com.api.controllers;

import br.com.api.domain.dtos.inventario.InventarioResponseDTO;
import br.com.api.domain.dtos.inventario.InventarioUpdateDTO;
import br.com.api.domain.dtos.inventario.ItemRequestDTO;
import br.com.api.domain.dtos.inventario.ItemResponseDTO;
import br.com.api.services.interfaces.InventarioService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.concurrent.ExecutionException;

@Path("/api/v1/agentes/{idFicha}/inventario")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class InventarioController {

    @Inject
    InventarioService service;

    @GET
    public Response getById(
            @PathParam("idFicha") String idFicha
    ) throws ExecutionException, InterruptedException {

        InventarioResponseDTO response = service.obterOuCriar(idFicha);

        return Response.ok(response).build();
    }

    @PATCH
    public Response patch (
            @PathParam("idFicha") String idFicha,
            @Valid InventarioUpdateDTO request
    ) throws ExecutionException, InterruptedException {

        service.atualizar(idFicha, request);

        return Response.ok().build();
    }

    // ========== ITENS ==========
    @GET
    @Path("/itens/{idItem}")
    public Response getItem(
            @PathParam("idFicha") String idFicha,
            @PathParam("idItem") String idItem
    ) throws ExecutionException, InterruptedException {

        ItemResponseDTO response = service.obterItemPorId(idFicha, idItem);

        return Response
                .ok(response)
                .build();
    }

    @POST
    @Path("/itens")
    public Response postItem(
            @PathParam("idFicha") String idFicha,
            @Valid ItemRequestDTO request) throws ExecutionException, InterruptedException {

        ItemResponseDTO response = service.adicionarItem(idFicha, request);

        return Response
                .status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    @PATCH
    @Path("/itens/{idItem}")
    public Response patchItem(
            @PathParam("idFicha") String idFicha,
            @PathParam("idItem") String idItem,
            @Valid ItemRequestDTO request
    ) throws ExecutionException, InterruptedException {
        service.atualizarItem(idFicha, idItem, request);

        return Response.ok().build();
    }

    @DELETE
    @Path("/itens/{idItem}")
    public Response deleteItem (
            @PathParam("idFicha") String idFicha,
            @PathParam("idItem") String idItem
    ) throws ExecutionException, InterruptedException {
        service.deletarItem(idFicha, idItem);

        return Response.noContent().build();
    }
}
