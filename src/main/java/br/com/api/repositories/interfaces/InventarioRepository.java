package br.com.api.repositories.interfaces;

import br.com.api.domain.entities.Inventario;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface InventarioRepository {
    Inventario persistir(String idFicha, Inventario inventario) throws ExecutionException, InterruptedException;
    Inventario obterPorId(String idFicha) throws ExecutionException, InterruptedException;
    void alterar(String idFicha, Map<String, Object> campos) throws ExecutionException, InterruptedException;
}
