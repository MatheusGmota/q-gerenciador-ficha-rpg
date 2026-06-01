package br.com.api.services.interfaces;

import br.com.api.domain.dtos.agente.AgenteCreateDTO;
import br.com.api.domain.dtos.agente.AgenteResponseDTO;
import br.com.api.domain.dtos.agente.AgenteResumoResponseDTO;
import br.com.api.domain.dtos.agente.AgenteUpdateDTO;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface AgenteService {
    List<AgenteResumoResponseDTO> obterTudo(String token) throws ExecutionException, InterruptedException;
    List<AgenteResumoResponseDTO> obterPorIdUsuario(String token) throws ExecutionException, InterruptedException;
    AgenteResponseDTO obter(String token, String idFicha) throws ExecutionException, InterruptedException;
    AgenteResponseDTO criar(String token, AgenteCreateDTO request) throws ExecutionException, InterruptedException;
    void atualizar(String token, String idFicha, AgenteUpdateDTO request) throws ExecutionException, InterruptedException;
    void deletar(String token, String idFicha) throws ExecutionException, InterruptedException;
}
