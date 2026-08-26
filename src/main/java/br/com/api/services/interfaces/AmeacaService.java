package br.com.api.services.interfaces;

import br.com.api.domain.dtos.ameaca.AmeacaResponseDTO;
import br.com.api.domain.dtos.ameaca.AmeacaResumoResponseDTO;
import br.com.api.domain.dtos.ameaca.AmeacaUpdateDTO;
import br.com.api.domain.dtos.pericias.PericiaUpdateDTO;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface AmeacaService {
    List<AmeacaResumoResponseDTO> obterTudo() throws ExecutionException, InterruptedException;
    List<AmeacaResumoResponseDTO> obterPorIdUsuario() throws ExecutionException, InterruptedException;
    AmeacaResponseDTO obter(String idFicha) throws ExecutionException, InterruptedException;
    AmeacaResponseDTO criar() throws ExecutionException, InterruptedException;
    void atualizar(String idFicha, AmeacaUpdateDTO request) throws ExecutionException, InterruptedException;
    void deletar(String idFicha) throws ExecutionException, InterruptedException;

    void atualizarPericia(String idFicha, PericiaUpdateDTO request) throws ExecutionException, InterruptedException;
}
