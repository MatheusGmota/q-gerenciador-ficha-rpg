package br.com.api.controllers;

import br.com.api.domain.dtos.ameaca.AmeacaResponseDTO;
import br.com.api.domain.dtos.ameaca.AmeacaResumoResponseDTO;
import br.com.api.domain.dtos.ameaca.AmeacaUpdateDTO;
import br.com.api.domain.dtos.pericias.PericiaUpdateDTO;
import br.com.api.services.interfaces.AmeacaService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Path("/api/v1/ameacas")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class AmeacaController {

    @Inject
    AmeacaService service;

    @GET
    @Path("/{idFicha}")
    public Response getById(@PathParam("idFicha") String idFicha) throws ExecutionException, InterruptedException {

        AmeacaResponseDTO obter = service.obter(idFicha);

        return Response.ok(obter).build();
    }

    @GET
    @Path("/usuario")
    public Response getAllByUserId() throws ExecutionException, InterruptedException {

        List<AmeacaResumoResponseDTO> obter = service.obterPorIdUsuario();

        return Response.ok(obter).build();
    }

    @GET
    @RolesAllowed("admin")
    public Response getAll() throws ExecutionException, InterruptedException {
        List<AmeacaResumoResponseDTO> obter = service.obterTudo();

        return Response.ok(obter).build();
    }

    @POST
    public Response post() throws ExecutionException, InterruptedException {
        AmeacaResponseDTO criar = service.criar();
        return Response
                .status(Response.Status.CREATED)
                .entity(criar)
                .build();
    }

    @PATCH
    @Path("/{idFicha}")
    public Response patch(
            @PathParam("idFicha") String idFicha,
            @Valid AmeacaUpdateDTO request
    ) throws ExecutionException, InterruptedException {

        service.atualizar(idFicha, request);

        return Response.ok().build();
    }

    @DELETE
    @Path("/{idFicha}")
    public Response delete(
            @PathParam("idFicha") String idFicha
    ) throws ExecutionException, InterruptedException {

        service.deletar(idFicha);

        return Response.noContent().build();
    }

    @PUT
    @Path("/{idFicha}/pericias")
    public Response putPericias(
            @PathParam("idFicha") String idFicha,
            @Valid PericiaUpdateDTO request
    ) throws ExecutionException, InterruptedException {


        service.atualizarPericia(idFicha, request);
        return Response
                .noContent()
                .build();
    }
}
