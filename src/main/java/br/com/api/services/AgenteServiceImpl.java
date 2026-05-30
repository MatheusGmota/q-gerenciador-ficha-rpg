package br.com.api.services;

import br.com.api.domain.dtos.agente.AgenteCreateDTO;
import br.com.api.domain.dtos.agente.AgenteResponseDTO;
import br.com.api.domain.dtos.agente.AgenteUpdateDTO;
import br.com.api.domain.entities.Agente;
import br.com.api.repositories.interfaces.AgenteRepository;
import br.com.api.services.interfaces.AgenteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import static br.com.api.domain.mappers.AgenteMapper.toAgente;
import static br.com.api.domain.mappers.AgenteMapper.toAgenteDto;
import static br.com.api.domain.util.ValidationUtil.validaCampos;


@ApplicationScoped
@Slf4j
public class AgenteServiceImpl implements AgenteService {

    private ObjectMapper objectMapper;

    @Inject
    AgenteRepository repository;

    @Inject
    AuthenticationService authService;

    @Override
    public AgenteResponseDTO obter(String token, String idFicha) throws ExecutionException, InterruptedException {
        String uid = authService.validarToken(token).getUid();
        Agente ficha = repository.obterPorId(idFicha)
                .orElseThrow(() -> new NotFoundException("Ficha com id:"+ idFicha +" não encontrada"));

        if (!ficha.getIdUsuario().equals(uid)) {
            log.info("Usuário id:{} tentou acessar ficha id:{} e não possui permissão", uid, idFicha);
            throw new WebApplicationException("Usuário não pode acessar ficha", Response.Status.UNAUTHORIZED);
        }

        return toAgenteDto(ficha);
    }

    @Override
    public AgenteResponseDTO criar(String token, AgenteCreateDTO request) throws ExecutionException, InterruptedException {
        String uid = authService.validarToken(token).getUid();
        Agente ficha = repository.persistirFicha(toAgente(uid, request));
        return toAgenteDto(ficha);
    }

    @Override
    public void atualizar(String token, String idFicha, AgenteUpdateDTO request) throws ExecutionException, InterruptedException {
        obter(token, idFicha); // valida token, ficha existente e permissão para editar ficha

        Map<String, Object> camposValidados = validaCampos(request);
        repository.alterarFicha(idFicha, camposValidados);
    }

    @Override
    public void deletar(String token, String idFicha) throws ExecutionException, InterruptedException {
        obter(token, idFicha); // valida token, ficha existente e permissão para editar ficha
        repository.deletarFicha(idFicha);
    }

}
