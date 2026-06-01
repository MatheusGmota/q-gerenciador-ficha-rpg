package br.com.api.services.interfaces;

import br.com.api.domain.dtos.habilidade.HabilidadeRequestDTO;
import br.com.api.domain.dtos.habilidade.HabilidadeResponseDTO;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface HabilidadeService {
    List<HabilidadeResponseDTO> obterTudo(String token, String idFicha) throws ExecutionException, InterruptedException;
    HabilidadeResponseDTO obterPorId(String token, String idFicha, String idHabilidade) throws ExecutionException, InterruptedException;
    HabilidadeResponseDTO adicionar(String token, String idFicha, HabilidadeRequestDTO request) throws ExecutionException, InterruptedException;
    void atualizar(String token, String idFicha, String idHabilidade, HabilidadeRequestDTO request) throws ExecutionException, InterruptedException;
    void deletar(String token, String idFicha, String idHabilidade) throws ExecutionException, InterruptedException;
}
