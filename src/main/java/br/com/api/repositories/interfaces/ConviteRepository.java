package br.com.api.repositories.interfaces;

import br.com.api.domain.dtos.convite.ResgatarConvite;
import br.com.api.domain.entities.subcollections.Convite;

import java.util.concurrent.ExecutionException;

public interface ConviteRepository {
    void criar(String idCampanha, Convite convite) throws ExecutionException, InterruptedException;

    ResgatarConvite resgatar(String token, String idUsuario, String nomeUsuario) throws ExecutionException, InterruptedException;
}