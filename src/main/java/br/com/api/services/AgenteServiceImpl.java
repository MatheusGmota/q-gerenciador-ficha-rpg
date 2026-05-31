package br.com.api.services;

import br.com.api.domain.dtos.agente.AgenteCreateDTO;
import br.com.api.domain.dtos.agente.AgenteResponseDTO;
import br.com.api.domain.dtos.agente.AgenteResumoResponseDTO;
import br.com.api.domain.dtos.agente.AgenteUpdateDTO;
import br.com.api.domain.dtos.habilidade.HabilidadeRequestDTO;
import br.com.api.domain.dtos.habilidade.HabilidadeResponseDTO;
import br.com.api.domain.entities.Agente;
import br.com.api.domain.entities.subcollections.Habilidade;
import br.com.api.domain.mappers.AgenteMapper;
import br.com.api.domain.mappers.HabilidadeMapper;
import br.com.api.repositories.interfaces.AgenteRepository;
import br.com.api.services.interfaces.AgenteService;
import com.google.firebase.auth.FirebaseToken;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static br.com.api.domain.mappers.AgenteMapper.*;
import static br.com.api.domain.mappers.HabilidadeMapper.*;
import static br.com.api.domain.util.ValidationUtil.validaCampos;

@ApplicationScoped
@Slf4j
public class AgenteServiceImpl implements AgenteService {

    @Inject
    AgenteRepository repository;

    @Inject
    AuthenticationService authService;

    @Override
    public List<AgenteResumoResponseDTO> obterTudo(String token) throws ExecutionException, InterruptedException {
        FirebaseToken decoded = authService.validarToken(token);
        if (Boolean.FALSE.equals(decoded.getClaims().get("admin"))) {
            throw new WebApplicationException("Usuário não possui permissão para acessar essa rota", Response.Status.FORBIDDEN);
        }

        return repository.obterTodasFichas()
                .stream().map(AgenteMapper::toAgenteResumoDto)
                .toList();
    }

    @Override
    public List<AgenteResumoResponseDTO> obterPorIdUsuario(String token) throws ExecutionException, InterruptedException {
        String uid = authService.validarToken(token).getUid(); // validar token

        return repository.obterFichasPorIdUsuario(uid)
                .stream().map(AgenteMapper::toAgenteResumoDto)
                .toList();
    }

    @Override
    public AgenteResponseDTO obter(String token, String idFicha) throws ExecutionException, InterruptedException {
        Agente ficha = validarAcessoFicha(token, idFicha);

        return toAgenteDto(ficha);
    }

    @Override
    public AgenteResponseDTO criar(String token, AgenteCreateDTO request) throws ExecutionException, InterruptedException {
        String uid = authService.validarToken(token).getUid();

        if (repository.excedeuLimiteMaxFichas(uid)) throw new WebApplicationException("Usuário atingiu o limite máximo de fichas");

        Agente ficha = repository.persistirFicha(toAgente(uid, request));
        return toAgenteDto(ficha);
    }

    @Override
    public void atualizar(String token, String idFicha, AgenteUpdateDTO request) throws ExecutionException, InterruptedException {
        validarAcessoFicha(token, idFicha); // valida token, ficha existente e permissão para editar ficha

        Map<String, Object> camposValidados = validaCampos(request);
        repository.alterarFicha(idFicha, camposValidados);
    }

    @Override
    public void deletar(String token, String idFicha) throws ExecutionException, InterruptedException {
        validarAcessoFicha(token, idFicha); // valida token, ficha existente e permissão para editar ficha
        repository.deletarFicha(idFicha);
    }

    // =============================== HABILIDADES ===============================
    @Override
    public List<HabilidadeResponseDTO> obterTodasHabilidades(
            String token,
            String idFicha
    ) throws ExecutionException, InterruptedException {

        validarAcessoFicha(token, idFicha);

        return repository.obterHabilidades(idFicha)
                .stream()
                .map(HabilidadeMapper::toHabilidadeDto)
                .toList();
    }

    @Override
    public HabilidadeResponseDTO adicionarHabilidade(
            String token,
            String idFicha,
            HabilidadeRequestDTO request
    ) throws ExecutionException, InterruptedException {

        validarAcessoFicha(token, idFicha);

        Habilidade habilidade =
                repository.persistirHabilidade(idFicha, toHabilidade(request));

        return toHabilidadeDto(habilidade);
    }

    @Override
    public void atualizarHabilidade(
            String token,
            String idFicha,
            String idHabilidade,
            HabilidadeRequestDTO request
    ) throws ExecutionException, InterruptedException {

        validarAcessoFicha(token, idFicha);

        if (!repository.existeDocHabilidade(idFicha, idHabilidade))
            throw new NotFoundException("Habilidade não encontrada");

        repository.atualizarHabilidade(idFicha, idHabilidade, toHabilidade(request));
    }

    @Override
    public void deletarHabilidade(
            String token,
            String idFicha,
            String idHabilidade
    ) throws ExecutionException, InterruptedException {

        obter(token, idFicha);

        if (!repository.existeDocHabilidade(idFicha, idHabilidade))
            throw new NotFoundException("Habilidade não encontrada");

        repository.deletarHabilidade(idFicha, idHabilidade);
    }

    private Agente validarAcessoFicha(
            String token,
            String idFicha
    ) throws ExecutionException, InterruptedException {

        String uid = authService.validarToken(token).getUid();

        Agente ficha = repository.obterPorId(idFicha)
                .orElseThrow(() ->
                        new NotFoundException("Ficha não encontrada"));

        if (!ficha.getIdUsuario().equals(uid)) {
            throw new WebApplicationException(
                    "Usuário não autorizado",
                    Response.Status.FORBIDDEN
            );
        }

        return ficha;
    }
}
