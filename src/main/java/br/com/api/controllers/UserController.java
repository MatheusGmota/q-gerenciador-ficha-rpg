package br.com.api.controllers;

import br.com.api.domain.dtos.user.ResetPasswordLink;
import br.com.api.domain.dtos.user.UpdateUserDTO;
import br.com.api.domain.dtos.user.UserResponseDTO;
import br.com.api.services.interfaces.UserService;
import com.google.firebase.auth.FirebaseAuthException;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static br.com.api.infra.security.AuthUtil.extractBearerToken;

@Path("/api/v1/user")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class UserController {

    @Inject
    UserService userService;

    @GET
    @Path("/{uid}")
    public Response getUserByUid(@PathParam("uid") String uid) throws FirebaseAuthException {
        UserResponseDTO user = userService.getUser(uid);

        return Response.ok(user).build();
    }

    @GET
    public Response getLoggedUser(@HeaderParam("Authorization") String authHeader) throws FirebaseAuthException {
        String token = extractBearerToken(authHeader);
        UserResponseDTO loggedUser = userService.getLoggedUser(token);

        return Response.ok(loggedUser).build();
    }

    @GET
    @Path("/resetPasswordLink")
    public Response getResetPasswordLink(
            @HeaderParam("Authorization") String authHeader,
            @QueryParam("for-email") String email
    ) throws FirebaseAuthException {
        String token = extractBearerToken(authHeader);
        ResetPasswordLink resetPasswordLink = userService.resetPassword(token, email);

        return Response.ok(resetPasswordLink).build();
    }

    @PATCH
    public Response updateUser(
            @HeaderParam("Authorization") String authHeader,
            @Valid UpdateUserDTO request
    ) throws FirebaseAuthException {
        String token = extractBearerToken(authHeader);
        userService.updateUser(token, request);

        return Response.noContent().build();
    }

    @DELETE
    public Response deleteUser( @HeaderParam("Authorization") String authHeader) throws FirebaseAuthException {
        String token = extractBearerToken(authHeader);
        userService.removeUser(token);

        return Response.noContent().build();
    }
}
