package br.com.api.services.interfaces;

import br.com.api.domain.dtos.campanha.CampanhaCreateDTO;
import br.com.api.domain.dtos.campanha.CampanhaResponseDTO;
import br.com.api.domain.dtos.campanha.CampanhaResumoResponseDTO;
import br.com.api.domain.dtos.campanha.CampanhaUpdateDTO;
import br.com.api.domain.dtos.convite.ConviteCreateDTO;
import br.com.api.domain.dtos.convite.ConviteResponseDTO;
import br.com.api.domain.dtos.membro.MembroResponseDTO;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface CampanhaService {
    List<CampanhaResumoResponseDTO> obterTudo(String token) throws ExecutionException, InterruptedException;
    List<CampanhaResumoResponseDTO> obterPorIdUsuario(String token) throws ExecutionException, InterruptedException;
    CampanhaResponseDTO obter(String token, String idCampanha) throws ExecutionException, InterruptedException;
    CampanhaResponseDTO criar(String token, CampanhaCreateDTO request) throws ExecutionException, InterruptedException;
    void atualizar(String token, String idCampanha, CampanhaUpdateDTO request) throws ExecutionException, InterruptedException;
    void deletar(String token, String idCampanha) throws ExecutionException, InterruptedException;

    List<MembroResponseDTO> obterMembros(String token, String idCampanha) throws ExecutionException, InterruptedException;
    void removerMembro(String token, String idCampanha, String idUsuarioAlvo) throws ExecutionException, InterruptedException;

    ConviteResponseDTO gerarConvite(String token, String idCampanha, ConviteCreateDTO request) throws ExecutionException, InterruptedException;

    MembroResponseDTO entrarPorConvite(String token, String tokenConvite) throws ExecutionException, InterruptedException;
}
