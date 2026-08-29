package br.com.api.services.interfaces;

import br.com.api.domain.dtos.agente.AgenteResponseDTO;
import br.com.api.domain.dtos.agente.AgenteUpdateDTO;
import br.com.api.domain.dtos.ameaca.AmeacaResponseDTO;
import br.com.api.domain.dtos.ameaca.AmeacaUpdateDTO;
import br.com.api.domain.dtos.fichavinculada.FichaVinculadaResponseDTO;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface FichaCampanhaService {

    FichaVinculadaResponseDTO vincularAgente(String idCampanha, String idFicha) throws ExecutionException, InterruptedException;
    FichaVinculadaResponseDTO vincularAmeaca(String idCampanha, String idFicha) throws ExecutionException, InterruptedException;
    void desvincularAgente(String idCampanha, String idFicha) throws ExecutionException, InterruptedException;
    void desvincularAmeaca(String idCampanha, String idFicha) throws ExecutionException, InterruptedException;
    List<FichaVinculadaResponseDTO> listarFichas(String idCampanha) throws ExecutionException, InterruptedException;
    AgenteResponseDTO obterAgente(String idCampanha, String idFicha) throws ExecutionException, InterruptedException;
    void atualizarAgente(String idCampanha, String idFicha, AgenteUpdateDTO request) throws ExecutionException, InterruptedException;
    AmeacaResponseDTO obterAmeaca(String idCampanha, String idFicha) throws ExecutionException, InterruptedException;
    void atualizarAmeaca(String idCampanha, String idFicha, AmeacaUpdateDTO request) throws ExecutionException, InterruptedException;
}