package br.com.api.services.interfaces;

import br.com.api.domain.dtos.agente.AgenteUpdateDTO;
import br.com.api.domain.dtos.agente.AgenteCreateDTO;
import br.com.api.domain.dtos.agente.AgenteResponseDTO;

import java.util.concurrent.ExecutionException;

public interface AgenteService {
    AgenteResponseDTO obter(String token, String idFicha) throws ExecutionException, InterruptedException;
    AgenteResponseDTO criar(String token, AgenteCreateDTO request) throws ExecutionException, InterruptedException;
    void atualizar(String token, String idFicha, AgenteUpdateDTO request) throws ExecutionException, InterruptedException;
    void deletar(String token, String idFicha) throws ExecutionException, InterruptedException;
}
