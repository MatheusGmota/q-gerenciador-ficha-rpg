package br.com.api.services.interfaces;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface FichaSubcollectionService<R, T> {
    List<R> obterTudo(String token, String idFicha) throws ExecutionException, InterruptedException;
    R obterPorId(String token, String idFicha, String id) throws ExecutionException, InterruptedException;
    R adicionar(String token, String idFicha, T request) throws ExecutionException, InterruptedException;
    void atualizar(String token, String idFicha, String id, T request) throws ExecutionException, InterruptedException;
    void deletar(String token, String idFicha, String id) throws ExecutionException, InterruptedException;
}
