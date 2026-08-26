package br.com.api.services.interfaces;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface FichaSubcollectionService<R, T> {
    List<R> obterTudo(String idFicha) throws ExecutionException, InterruptedException;
    R obterPorId(String idFicha, String id) throws ExecutionException, InterruptedException;
    R adicionar(String idFicha, T request) throws ExecutionException, InterruptedException;
    void atualizar(String idFicha, String id, T request) throws ExecutionException, InterruptedException;
    void deletar(String idFicha, String id) throws ExecutionException, InterruptedException;
}
