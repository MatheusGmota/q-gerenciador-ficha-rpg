package br.com.api.services;

import br.com.api.domain.dtos.ritual.RitualRequestDTO;
import br.com.api.domain.dtos.ritual.RitualResponseDTO;
import br.com.api.domain.entities.subcollections.Ritual;
import br.com.api.domain.mappers.RitualMapper;
import br.com.api.repositories.RitualRepositoryImpl;
import br.com.api.services.interfaces.RitualService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
public class RitualServiceImpl extends AbstractSubcollectionService implements RitualService {

    @Inject
    RitualRepositoryImpl repository;

    @Inject
    RitualMapper mapper;

    @Override
    public List<RitualResponseDTO> obterTudo(String token, String idFicha) throws ExecutionException, InterruptedException {
        validarAcessoFicha(token, idFicha);

        List<Ritual> rituais = repository.procurarTudo(idFicha);
        if (rituais.isEmpty()) return List.of();

        return rituais
                .stream()
                .map(mapper::toRitualDto)
                .toList();
    }

    @Override
    public RitualResponseDTO obterPorId(String token, String idFicha, String idRitual) throws ExecutionException, InterruptedException {
        validarAcessoFicha(token, idFicha);

        Ritual ritual = repository.procurarPorId(idFicha, idRitual)
                .orElseThrow(() -> new NotFoundException("Ritual não encontrada"));

        return mapper.toRitualDto(ritual);
    }

    @Override
    public RitualResponseDTO adicionar(String token, String idFicha, RitualRequestDTO request) throws ExecutionException, InterruptedException {
        validarAcessoFicha(token, idFicha);

        Ritual ritual =
                repository.persistir(idFicha, mapper.toRitual(request));

        return mapper.toRitualDto(ritual);
    }

    @Override
    public void atualizar(String token, String idFicha, String idRitual, RitualRequestDTO request) throws ExecutionException, InterruptedException {
        validarAcessoFicha(token, idFicha);

        if (!repository.existeDocumento(idFicha, idRitual))
            throw new NotFoundException("Ritual não encontrada");

        repository.editar(idFicha, idRitual, mapper.toRitual(request));
    }

    @Override
    public void deletar(String token, String idFicha, String idRitual) throws ExecutionException, InterruptedException {
        validarAcessoFicha(token, idFicha);

        if (!repository.existeDocumento(idFicha, idRitual))
            throw new NotFoundException("Ritual não encontrada");

        repository.remover(idFicha, idRitual);
    }
}
