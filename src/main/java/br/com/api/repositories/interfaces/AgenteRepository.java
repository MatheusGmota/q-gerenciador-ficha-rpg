package br.com.api.repositories.interfaces;

import br.com.api.domain.entities.Agente;
import br.com.api.domain.entities.subcollections.Habilidade;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface AgenteRepository {
    // =============================== AGENTE ===============================
    List<Agente> obterFichasPorIdUsuario(String idUsuario) throws ExecutionException, InterruptedException;
    List<Agente> obterTodasFichas() throws ExecutionException, InterruptedException;
    Optional<Agente> obterPorId(String idFicha) throws ExecutionException, InterruptedException;
    Agente persistirFicha(Agente agente) throws ExecutionException, InterruptedException;
    void alterarFicha(String idFicha, Map<String, Object> campos) throws ExecutionException, InterruptedException;
    void deletarFicha(String idFicha);

    boolean excedeuLimiteMaxFichas(String idUsuario)  throws ExecutionException, InterruptedException;

    // =============================== HABILIDADES ===============================
    List<Habilidade> obterHabilidades(String idFicha) throws ExecutionException, InterruptedException;
    Habilidade persistirHabilidade(String idFicha, Habilidade habilidade) throws ExecutionException, InterruptedException;
    void atualizarHabilidade(String idFicha, String idHabilidade, Habilidade habilidade) throws ExecutionException, InterruptedException;
    void deletarHabilidade(String idFicha, String idHabilidade);
    boolean existeDocHabilidade(String idFicha, String idHabilidade) throws ExecutionException, InterruptedException;

}
