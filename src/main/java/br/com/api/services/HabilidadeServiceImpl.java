package br.com.api.services;

import br.com.api.domain.dtos.habilidade.HabilidadeRequestDTO;
import br.com.api.domain.dtos.habilidade.HabilidadeResponseDTO;
import br.com.api.domain.entities.subcollections.Habilidade;
import br.com.api.domain.mappers.HabilidadeMapper;
import br.com.api.repositories.HabilidadeRepositoryImpl;
import br.com.api.services.interfaces.HabilidadeService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static br.com.api.domain.mappers.HabilidadeMapper.toHabilidade;
import static br.com.api.domain.mappers.HabilidadeMapper.toHabilidadeDto;

@ApplicationScoped
public class HabilidadeServiceImpl extends GenericService implements HabilidadeService {

    @Inject
    HabilidadeRepositoryImpl repository;

    @Override
    public List<HabilidadeResponseDTO> obterTudo(
            String token,
            String idFicha
    ) throws ExecutionException, InterruptedException {

        validarAcessoFicha(token, idFicha);

        return repository.procurarTudo(idFicha)
                .stream()
                .map(HabilidadeMapper::toHabilidadeDto)
                .toList();
    }

    @Override
    public HabilidadeResponseDTO obterPorId(String token, String idFicha, String idHabilidade) throws ExecutionException, InterruptedException {

        validarAcessoFicha(token, idFicha);

        Habilidade habilidade = repository.procurarPorId(idFicha, idHabilidade)
                .orElseThrow(() -> new NotFoundException("Ficha não encontrada"));

        return toHabilidadeDto(habilidade);
    }

    @Override
    public HabilidadeResponseDTO adicionar(
            String token,
            String idFicha,
            HabilidadeRequestDTO request
    ) throws ExecutionException, InterruptedException {

        validarAcessoFicha(token, idFicha);

        Habilidade habilidade =
                repository.persistir(idFicha, toHabilidade(request));

        return toHabilidadeDto(habilidade);
    }

    @Override
    public void atualizar(
            String token,
            String idFicha,
            String idHabilidade,
            HabilidadeRequestDTO request
    ) throws ExecutionException, InterruptedException {

        validarAcessoFicha(token, idFicha);

        if (!repository.existeDocumento(idFicha, idHabilidade))
            throw new NotFoundException("Habilidade não encontrada");

        repository.editar(idFicha, idHabilidade, toHabilidade(request));
    }

    @Override
    public void deletar(
            String token,
            String idFicha,
            String idHabilidade
    ) throws ExecutionException, InterruptedException {

        validarAcessoFicha(token, idFicha);

        if (!repository.existeDocumento(idFicha, idHabilidade))
            throw new NotFoundException("Habilidade não encontrada");

        repository.remover(idFicha, idHabilidade);
    }
}
