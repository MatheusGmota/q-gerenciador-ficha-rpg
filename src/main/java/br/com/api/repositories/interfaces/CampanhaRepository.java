package br.com.api.repositories.interfaces;

import br.com.api.domain.entities.Campanha;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface CampanhaRepository {
    List<Campanha> obterPorIdUsuario(String idUsuario) throws ExecutionException, InterruptedException;
    List<Campanha> obterTodas() throws ExecutionException, InterruptedException;
    Optional<Campanha> obterPorId(String id) throws ExecutionException, InterruptedException;
    Campanha persistir(Campanha agente) throws ExecutionException, InterruptedException;
    void alterar(String id, Map<String, Object> campos) throws ExecutionException, InterruptedException;
    void deletar(String id) throws ExecutionException, InterruptedException;
}
