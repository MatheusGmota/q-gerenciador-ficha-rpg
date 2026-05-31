package br.com.api.services.interfaces;

import br.com.api.domain.dtos.agente.AgenteResumoResponseDTO;
import br.com.api.domain.dtos.agente.AgenteUpdateDTO;
import br.com.api.domain.dtos.agente.AgenteCreateDTO;
import br.com.api.domain.dtos.agente.AgenteResponseDTO;
import br.com.api.domain.dtos.habilidade.HabilidadeRequestDTO;
import br.com.api.domain.dtos.habilidade.HabilidadeResponseDTO;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface AgenteService {
    // =============================== AGENTE ===============================
    List<AgenteResumoResponseDTO> obterTudo(String token) throws ExecutionException, InterruptedException;
    List<AgenteResumoResponseDTO> obterPorIdUsuario(String token) throws ExecutionException, InterruptedException;
    AgenteResponseDTO obter(String token, String idFicha) throws ExecutionException, InterruptedException;
    AgenteResponseDTO criar(String token, AgenteCreateDTO request) throws ExecutionException, InterruptedException;
    void atualizar(String token, String idFicha, AgenteUpdateDTO request) throws ExecutionException, InterruptedException;
    void deletar(String token, String idFicha) throws ExecutionException, InterruptedException;

    // =============================== HABILIDADES ===============================
    List<HabilidadeResponseDTO> obterTodasHabilidades(String token, String idFicha) throws ExecutionException, InterruptedException;
    HabilidadeResponseDTO adicionarHabilidade(String token, String idFicha, HabilidadeRequestDTO request) throws ExecutionException, InterruptedException;
    void atualizarHabilidade(String token, String idFicha, String idHabilidade, HabilidadeRequestDTO request) throws ExecutionException, InterruptedException;
    void deletarHabilidade(String token, String idFicha, String idHabilidade) throws ExecutionException, InterruptedException;
}
