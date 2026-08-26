package br.com.api.repositories.interfaces;

import br.com.api.domain.entities.subcollections.FichaVinculada;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface FichaVinculadaRepository {
    FichaVinculada vincularComValidacao(String idCampanha, FichaVinculada vinculo, boolean ehMestre) throws ExecutionException, InterruptedException;
    Optional<FichaVinculada> obterPorId(String idCampanha, String idFicha) throws ExecutionException, InterruptedException;
    List<FichaVinculada> obterTodas(String idCampanha) throws ExecutionException, InterruptedException;
    List<FichaVinculada> obterPorUsuario(String idCampanha, String idUsuario) throws ExecutionException, InterruptedException;
    void desvincular(String idCampanha, String idFicha) throws ExecutionException, InterruptedException;
    void desvincularTodasDaCampanha(String idCampanha) throws ExecutionException, InterruptedException;
}