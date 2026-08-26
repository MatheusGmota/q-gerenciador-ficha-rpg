package br.com.api.controllers;

import br.com.api.domain.dtos.agente.AgenteCreateDTO;
import br.com.api.domain.dtos.agente.AgenteResponseDTO;
import br.com.api.domain.dtos.agente.AgenteResumoResponseDTO;
import br.com.api.domain.dtos.agente.AgenteUpdateDTO;
import br.com.api.domain.dtos.pericias.PericiaUpdateDTO;
import br.com.api.domain.dtos.pericias.PericiasAtributoDTO;
import br.com.api.services.interfaces.AgenteService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Path("/api/v1/agentes")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated // toda rota exige token válido; sobrescrita pontual com @RolesAllowed quando preciso
public class AgenteController {

    @Inject
    AgenteService service;

    @GET
    @Path("/{idFicha}")
    public Response getById(@PathParam("idFicha") String idFicha) throws ExecutionException, InterruptedException {
        AgenteResponseDTO obter = service.obter(idFicha);
        return Response.ok(obter).build();
    }

    @GET
    @Path("/usuario")
    public Response getAllByUserId() throws ExecutionException, InterruptedException {
        List<AgenteResumoResponseDTO> obter = service.obterPorIdUsuario();
        return Response.ok(obter).build();
    }

    @GET
    @RolesAllowed("admin")
    public Response getAll() throws ExecutionException, InterruptedException {
        List<AgenteResumoResponseDTO> obter = service.obterTudo();
        return Response.ok(obter).build();
    }

    @POST
    public Response post(@Valid AgenteCreateDTO request) throws ExecutionException, InterruptedException {
        AgenteResponseDTO criar = service.criar(request);
        return Response
                .status(Response.Status.CREATED)
                .entity(criar)
                .build();
    }

    @PATCH
    @Path("/{idFicha}")
    public Response patch(
            @PathParam("idFicha") String idFicha,
            @Valid AgenteUpdateDTO request
    ) throws ExecutionException, InterruptedException {
        service.atualizar(idFicha, request);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{idFicha}")
    public Response delete(@PathParam("idFicha") String idFicha) throws ExecutionException, InterruptedException {
        service.deletar(idFicha);
        return Response.noContent().build();
    }

    // ================ PERICIAS ====================
    @GET
    @Path("/{idFicha}/pericias")
    public Response getPericias(@PathParam("idFicha") String idFicha) throws ExecutionException, InterruptedException {
        PericiasAtributoDTO obter = service.obterPericias(idFicha);
        return Response.ok(obter).build();
    }

    @PUT
    @Path("/{idFicha}/pericias")
    public Response putPericias(
            @PathParam("idFicha") String idFicha,
            @Valid PericiaUpdateDTO request
    ) throws ExecutionException, InterruptedException {
        service.atualizarPericia(idFicha, request);
        return Response.noContent().build();
    }
}