package br.com.api.services.interfaces;

import br.com.api.domain.dtos.membro.MembroResponseDTO;

import java.util.concurrent.ExecutionException;

public interface ConviteService {
    MembroResponseDTO entrarPorConvite(String token, String tokenConvite) throws ExecutionException, InterruptedException;
}
