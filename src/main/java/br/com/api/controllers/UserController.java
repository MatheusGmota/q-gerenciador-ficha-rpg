package br.com.api.controllers;

import br.com.api.domain.dtos.user.UpdateUserDTO;
import br.com.api.services.interfaces.UserService;
import com.google.firebase.auth.FirebaseAuthException;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/user")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class UserController {

    @Inject
    UserService userService;

    @GET
    @Path("/{uid}")
    public Response getUserByUid(@PathParam("uid") String uid) throws FirebaseAuthException {
        return Response.ok(userService.getUser(uid)).build();
    }

    @GET
    public Response getLoggedUser(@HeaderParam("Authorization") String authHeader) throws FirebaseAuthException {
        return Response
                .ok(userService.getLoggedUser(authHeader.replace("Bearer", "").trim()))
                .build();
    }

    @GET
    @Path("/resetPasswordLink")
    public Response getResetPasswordLink(
            @HeaderParam("Authorization") String authHeader,
            @QueryParam("for-email") String email
    ) throws FirebaseAuthException {
        return Response.ok(userService.resetPassword(
                authHeader.replace("Bearer", "").trim(),
                email)).build();
    }

    @PATCH
    public Response updateUser(
            @HeaderParam("Authorization") String authHeader,
            @Valid UpdateUserDTO request
    ) throws FirebaseAuthException {
        userService.updateUser(
                authHeader.replace("Bearer", "").trim(),
                request
        );

        return Response.noContent().build();
    }

    @DELETE
    public Response deleteUser( @HeaderParam("Authorization") String authHeader) throws FirebaseAuthException {
        userService.removeUser(
                authHeader.replace("Bearer", "").trim()
        );

        return Response.noContent().build();
    }
}
