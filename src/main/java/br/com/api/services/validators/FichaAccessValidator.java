package br.com.api.services.validators;

import br.com.api.domain.entities.Agente;
import br.com.api.domain.entities.Ameaca;
import br.com.api.repositories.interfaces.AgenteRepository;
import br.com.api.repositories.interfaces.AmeacaRepository;
import br.com.api.services.AuthenticationService;
import com.google.firebase.auth.FirebaseToken;
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

    @Inject
    AmeacaRepository ameacaRepository;

    public Agente validarAcessoFicha(
            String token,
            String idFicha
    ) throws ExecutionException, InterruptedException {

        FirebaseToken firebaseToken = authService.validarToken(token);
        String uid = firebaseToken.getUid();

        Agente ficha = repository.obterPorId(idFicha)
                .orElseThrow(() ->
                        new NotFoundException("Ficha não encontrada"));

        if (verificaAdmin(firebaseToken)) return ficha;

        if (!ficha.getIdUsuario().equals(uid)) {
            throw new WebApplicationException(
                    "Usuário não autorizado",
                    Response.Status.FORBIDDEN
            );
        }

        return ficha;
    }

    public Ameaca validarAcessoFichaAmeaca(
            String token,
            String idFicha
    ) throws ExecutionException, InterruptedException {

        FirebaseToken firebaseToken = authService.validarToken(token);
        String uid = firebaseToken.getUid();

        Ameaca ficha = ameacaRepository.obterPorId(idFicha)
                .orElseThrow(() ->
                        new NotFoundException("Ficha não encontrada"));

        if (verificaAdmin(firebaseToken)) return ficha;

        if (!ficha.getIdUsuario().equals(uid)) {
            throw new WebApplicationException(
                    "Usuário não autorizado",
                    Response.Status.FORBIDDEN
            );
        }

        return ficha;
    }

    private boolean verificaAdmin(FirebaseToken firebaseToken) throws ExecutionException, InterruptedException {
        Object admin = firebaseToken.getClaims().get("admin");

        if (admin == null) return false;
        return Boolean.TRUE.equals(admin);
    }
}
