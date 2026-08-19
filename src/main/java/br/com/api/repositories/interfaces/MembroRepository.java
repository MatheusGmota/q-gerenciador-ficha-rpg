package br.com.api.repositories.interfaces;

import br.com.api.domain.entities.subcollections.MembroCampanha;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface MembroRepository {
    void adicionar(String idCampanha, String idUsuario, MembroCampanha membroCampanha) throws ExecutionException, InterruptedException;

    Optional<MembroCampanha> obterPorCampanhaEUsuario(String idCampanha, String idUsuario) throws ExecutionException, InterruptedException;

    List<MembroCampanha> obterTodosPorCampanha(String idCampanha) throws ExecutionException, InterruptedException;

    void remover(String idCampanha, String idUsuario) throws ExecutionException, InterruptedException;

    void deletarPorCampanha(String idCampanha) throws ExecutionException, InterruptedException;
}
