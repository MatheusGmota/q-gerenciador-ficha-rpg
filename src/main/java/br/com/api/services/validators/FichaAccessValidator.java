package br.com.api.services.validators;

import br.com.api.domain.entities.Agente;
import br.com.api.repositories.interfaces.AgenteRepository;
import br.com.api.services.AuthenticationService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.concurrent.ExecutionException;

@ApplicationScoped
public class FichaAccessValidator {

    @Inject
    AuthenticationService authService;

    @Inject
    AgenteRepository repository;

    public Agente validarAcessoFicha(
            String token,
            String idFicha
    ) throws ExecutionException, InterruptedException {

        String uid = authService.validarToken(token).getUid();

        Agente ficha = repository.obterPorId(idFicha)
                .orElseThrow(() ->
                        new NotFoundException("Ficha não encontrada"));

        if (!ficha.getIdUsuario().equals(uid)) {
            throw new WebApplicationException(
                    "Usuário não autorizado",
                    Response.Status.FORBIDDEN
            );
        }

        return ficha;
    }
}
