package br.com.api.services.validators;

import br.com.api.domain.entities.Agente;
import br.com.api.domain.entities.Ameaca;
import br.com.api.infra.security.FirebaseUserPrincipal;
import br.com.api.repositories.interfaces.AgenteRepository;
import br.com.api.repositories.interfaces.AmeacaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.concurrent.ExecutionException;

@ApplicationScoped
public class FichaAccessValidator {

    @Inject
    FirebaseUserPrincipal currentUser;

    @Inject
    AgenteRepository repository;

    @Inject
    AmeacaRepository ameacaRepository;



    public Agente validarAcessoFicha(String idFicha) throws ExecutionException, InterruptedException {
        Agente ficha = repository.obterPorId(idFicha)
                .orElseThrow(() -> new NotFoundException("Ficha não encontrada"));

        if (currentUser.isAdmin()) return ficha;

        if (!ficha.getIdUsuario().equals(currentUser.getUid())) {
            throw new WebApplicationException("Usuário não autorizado", Response.Status.FORBIDDEN);
        }

        return ficha;
    }

    public Ameaca validarAcessoFichaAmeaca(String idFicha) throws ExecutionException, InterruptedException {
        Ameaca ficha = ameacaRepository.obterPorId(idFicha)
                .orElseThrow(() -> new NotFoundException("Ficha não encontrada"));

        if (currentUser.isAdmin()) return ficha;

        if (!ficha.getIdUsuario().equals(currentUser.getUid())) {
            throw new WebApplicationException("Usuário não autorizado", Response.Status.FORBIDDEN);
        }

        return ficha;
    }
}
