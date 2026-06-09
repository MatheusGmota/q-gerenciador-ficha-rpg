package br.com.api.services;

import br.com.api.domain.dtos.agente.AgenteCreateDTO;
import br.com.api.domain.dtos.agente.AgenteResponseDTO;
import br.com.api.domain.dtos.agente.AgenteResumoResponseDTO;
import br.com.api.domain.dtos.agente.AgenteUpdateDTO;
import br.com.api.domain.dtos.pericias.PericiaDTO;
import br.com.api.domain.dtos.pericias.PericiaUpdateDTO;
import br.com.api.domain.dtos.pericias.PericiasAtributoDTO;
import br.com.api.domain.entities.Agente;
import br.com.api.domain.enums.TipoAtributo;
import br.com.api.domain.enums.TipoPericia;
import br.com.api.domain.factories.AgenteFactory;
import br.com.api.domain.mappers.AgenteMapper;
import br.com.api.domain.model.Pericia;
import br.com.api.repositories.interfaces.AgenteRepository;
import br.com.api.services.interfaces.AgenteService;
import br.com.api.services.interfaces.InventarioService;
import br.com.api.services.validators.FichaAccessValidator;
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

import static br.com.api.util.ValidationUtil.validaCampos;
import static java.util.stream.Collectors.*;

@ApplicationScoped
@Slf4j
public class AgenteServiceImpl implements AgenteService {

    @Inject
    AgenteMapper mapper;

    @Inject
    AgenteFactory agenteFactory;

    @Inject
    AgenteRepository repository;

    @Inject
    FichaAccessValidator accessValidator;

    @Inject
    AuthenticationService authService;

    @Inject
    InventarioService inventarioService;

    @Override
    public List<AgenteResumoResponseDTO> obterTudo(String token) throws ExecutionException, InterruptedException {
        FirebaseToken decoded = authService.validarToken(token);
        if (Boolean.FALSE.equals(decoded.getClaims().get("admin"))) {
            throw new WebApplicationException("Usuário não possui permissão para acessar essa rota", Response.Status.FORBIDDEN);
        }

        return repository.obterTodasFichas()
                .stream().map(mapper::toAgenteResumoDto)
                .toList();
    }

    @Override
    public List<AgenteResumoResponseDTO> obterPorIdUsuario(String token) throws ExecutionException, InterruptedException {
        String uid = authService.validarToken(token).getUid(); // validar token

        return repository.obterFichasPorIdUsuario(uid)
                .stream().map(mapper::toAgenteResumoDto)
                .toList();
    }

    @Override
    public AgenteResponseDTO obter(String token, String idFicha) throws ExecutionException, InterruptedException {
        Agente ficha = accessValidator.validarAcessoFicha(token, idFicha);

        return mapper.toAgenteDto(ficha);
    }

    @Override
    public AgenteResponseDTO criar(String token, AgenteCreateDTO request) throws ExecutionException, InterruptedException {
        String uid = authService.validarToken(token).getUid();

        if (repository.excedeuLimiteMaxFichas(uid)) throw new WebApplicationException("Usuário atingiu o limite máximo de fichas");

        Agente ficha = repository.persistirFicha(
                agenteFactory.criar(uid, request)
        );

        inventarioService.inicializar(ficha.getId());

        return mapper.toAgenteDto(ficha);
    }

    @Override
    public void atualizar(String token, String idFicha, AgenteUpdateDTO request) throws ExecutionException, InterruptedException {
        accessValidator.validarAcessoFicha(token, idFicha);

        Map<String, Object> camposValidados = validaCampos(request);
        repository.alterarFicha(idFicha, camposValidados);
    }

    @Override
    public void deletar(String token, String idFicha) throws ExecutionException, InterruptedException {
        accessValidator.validarAcessoFicha(token, idFicha);
        repository.deletarFicha(idFicha);
    }

    @Override
    public PericiasAtributoDTO obterPericias(String token, String idFicha) throws ExecutionException, InterruptedException {
        Agente ficha = accessValidator.validarAcessoFicha(token, idFicha);

        Map<TipoAtributo, List<PericiaDTO>> agrupadas = agruparPorAtributo(ficha.getPericias());
        return mapper.toPericiasAtributoDto(agrupadas);
    }

    @Override
    public void atualizarPericia(String token, String idFicha, PericiaUpdateDTO request) throws ExecutionException, InterruptedException {
        Agente ficha = accessValidator.validarAcessoFicha(token, idFicha);

        String chave = request.nome().name().toLowerCase();
        if (!ficha.getPericias().containsKey(chave)) {
            throw new NotFoundException("Perícia '%s' não encontrada."
                    .formatted(request.nome())
            );
        }

        repository.atualizarPericia(idFicha, chave, mapper.toPericia(request));
    }

    private Map<TipoAtributo, List<PericiaDTO>> agruparPorAtributo(Map<String, Pericia> pericias) {
        return pericias.entrySet().stream()
                .collect(groupingBy(
                        k -> TipoPericia.valueOf(k.getKey().toUpperCase())
                                .getAtributo(),
                        mapping(
                                e -> new PericiaDTO(
                                        e.getKey(),
                                        e.getValue().isTreinado(),
                                        e.getValue().getTesteBase(),
                                        e.getValue().getBonus(),
                                        e.getValue().getBonusDescricao()
                                ),
                                toList()
                        ))
                );
    }
}
