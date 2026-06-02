package br.com.api.services;

import br.com.api.services.validators.FichaAccessValidator;
import jakarta.inject.Inject;

import java.util.concurrent.ExecutionException;

public abstract class AbstractSubcollectionService {

    @Inject
    FichaAccessValidator accessValidator;

    protected void validarAcessoFicha(String token, String idFicha) throws ExecutionException, InterruptedException {
        accessValidator.validarAcessoFicha(token, idFicha);
    }
}
