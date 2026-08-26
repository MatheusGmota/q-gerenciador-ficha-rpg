package br.com.api.services.interfaces;

import br.com.api.domain.dtos.agente.AgenteCreateDTO;
import br.com.api.domain.dtos.agente.AgenteResponseDTO;
import br.com.api.domain.dtos.agente.AgenteResumoResponseDTO;
import br.com.api.domain.dtos.agente.AgenteUpdateDTO;
import br.com.api.domain.dtos.pericias.PericiaUpdateDTO;
import br.com.api.domain.dtos.pericias.PericiasAtributoDTO;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface AgenteService {
    List<AgenteResumoResponseDTO> obterTudo() throws ExecutionException, InterruptedException;
    List<AgenteResumoResponseDTO> obterPorIdUsuario() throws ExecutionException, InterruptedException;
    AgenteResponseDTO obter(String idFicha) throws ExecutionException, InterruptedException;
    AgenteResponseDTO criar(AgenteCreateDTO request) throws ExecutionException, InterruptedException;
    void atualizar(String idFicha, AgenteUpdateDTO request) throws ExecutionException, InterruptedException;
    void deletar(String idFicha) throws ExecutionException, InterruptedException;
    PericiasAtributoDTO obterPericias(String idFicha) throws ExecutionException, InterruptedException;
    void atualizarPericia(String idFicha, PericiaUpdateDTO request) throws ExecutionException, InterruptedException;
}
