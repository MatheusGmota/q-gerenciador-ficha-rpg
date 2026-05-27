package br.com.api.controllers;

import br.com.api.domain.dtos.user.CreateUserDTO;
import br.com.api.domain.dtos.user.LoginDTO;
import br.com.api.services.interfaces.UserService;
import com.google.firebase.auth.FirebaseAuthException;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/auth")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AuthController {

    @Inject
    UserService userService;

    @POST
    @Path("/login")
    public Response login(@Valid LoginDTO request) throws FirebaseAuthException, InterruptedException {
        return Response.ok(userService.login(request)).build();
    }

    @POST
    @Path("/signup")
    public Response createUser(@Valid CreateUserDTO request) throws FirebaseAuthException, InterruptedException {
        return Response
                .status(Response.Status.CREATED)
                .entity(userService.addUser(request))
                .build();
    }
}
