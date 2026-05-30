package br.com.api.repositories.interfaces;

import br.com.api.domain.entities.Agente;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface AgenteRepository {
    Optional<Agente> obterPorId(String idFicha) throws ExecutionException, InterruptedException;
    Agente persistirFicha(Agente agente) throws ExecutionException, InterruptedException;
    void alterarFicha(String idFicha, Map<String, Object> campos) throws ExecutionException, InterruptedException;
    void deletarFicha(String idFicha);
}
