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
    List<CampanhaResumoResponseDTO> obterTudo() throws ExecutionException, InterruptedException;
    List<CampanhaResumoResponseDTO> obterPorIdUsuario() throws ExecutionException, InterruptedException;
    CampanhaResponseDTO obter(String idCampanha) throws ExecutionException, InterruptedException;
    CampanhaResponseDTO criar(CampanhaCreateDTO request) throws ExecutionException, InterruptedException;
    void atualizar(String idCampanha, CampanhaUpdateDTO request) throws ExecutionException, InterruptedException;
    void deletar(String idCampanha) throws ExecutionException, InterruptedException;

    List<MembroResponseDTO> obterMembros(String idCampanha) throws ExecutionException, InterruptedException;
    void removerMembro(String idCampanha, String idUsuarioAlvo) throws ExecutionException, InterruptedException;

    ConviteResponseDTO gerarConvite(String idCampanha, ConviteCreateDTO request) throws ExecutionException, InterruptedException;

    MembroResponseDTO entrarPorConvite(String tokenConvite) throws ExecutionException, InterruptedException;
}
