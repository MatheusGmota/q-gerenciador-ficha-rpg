package br.com.api.repositories.interfaces;

import br.com.api.domain.model.Pericia;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface FichaRepository<T> {
    List<T> obterFichasPorIdUsuario(String idUsuario) throws ExecutionException, InterruptedException;
    List<T> obterTodasFichas() throws ExecutionException, InterruptedException;
    Optional<T> obterPorId(String idFicha) throws ExecutionException, InterruptedException;
    T persistirFicha(T agente) throws ExecutionException, InterruptedException;
    void alterarFicha(String idFicha, Map<String, Object> campos) throws ExecutionException, InterruptedException;
    void deletarFicha(String idFicha) throws ExecutionException, InterruptedException;

    boolean excedeuLimiteMaxFichas(String idUsuario)  throws ExecutionException, InterruptedException;

    void atualizarPericia(String idFicha, String chave, Pericia pericia);
}
