package br.com.api.services;

import br.com.api.domain.dtos.ameaca.AmeacaResponseDTO;
import br.com.api.domain.dtos.ameaca.AmeacaResumoResponseDTO;
import br.com.api.domain.dtos.ameaca.AmeacaUpdateDTO;
import br.com.api.domain.dtos.pericias.PericiaDTO;
import br.com.api.domain.dtos.pericias.PericiaUpdateDTO;
import br.com.api.domain.entities.Ameaca;
import br.com.api.domain.enums.TipoAtributo;
import br.com.api.domain.enums.TipoPericia;
import br.com.api.domain.factories.AmeacaFactory;
import br.com.api.domain.mappers.AmeacaMapper;
import br.com.api.domain.model.Pericia;
import br.com.api.repositories.interfaces.AmeacaRepository;
import br.com.api.services.interfaces.AmeacaService;
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

import static br.com.api.services.validators.ValidationUtil.validaCampos;
import static java.util.stream.Collectors.*;

@ApplicationScoped
@Slf4j
public class AmeacaServiceImpl implements AmeacaService {

    @Inject
    AmeacaMapper mapper;

    @Inject
    AmeacaFactory ameacaFactory;

    @Inject
    AmeacaRepository repository;

    @Inject
    FichaAccessValidator accessValidator;

    @Inject
    AuthenticationService authService;

    @Override
    public List<AmeacaResumoResponseDTO> obterTudo(String token) throws ExecutionException, InterruptedException {
        FirebaseToken decoded = authService.validarToken(token);
        if (Boolean.FALSE.equals(decoded.getClaims().get("admin"))) {
            throw new WebApplicationException("Usuário não possui permissão para acessar essa rota", Response.Status.FORBIDDEN);
        }

        return repository.obterTodasFichas()
                .stream().map(mapper::toAmeacaResumoDto)
                .toList();
    }

    @Override
    public List<AmeacaResumoResponseDTO> obterPorIdUsuario(String token) throws ExecutionException, InterruptedException {
        String uid = authService.validarToken(token).getUid(); // validar token

        return repository.obterFichasPorIdUsuario(uid)
                .stream().map(mapper::toAmeacaResumoDto)
                .toList();
    }

    @Override
    public AmeacaResponseDTO obter(String token, String idFicha) throws ExecutionException, InterruptedException {
        Ameaca ficha = accessValidator.validarAcessoFichaAmeaca(token, idFicha);

        return mapper.toAmeacaDto(ficha);
    }

    public AmeacaResponseDTO criar(String token) throws ExecutionException, InterruptedException {
        String uid = authService.validarToken(token).getUid();

        if (repository.excedeuLimiteMaxFichas(uid)) throw new WebApplicationException("Usuário atingiu o limite máximo de fichas");

        Ameaca ficha = repository.persistirFicha(
                ameacaFactory.criar(uid)
        );

        return mapper.toAmeacaDto(ficha);
    }

    @Override
    public void atualizar(String token, String idFicha, AmeacaUpdateDTO request) throws ExecutionException, InterruptedException {
        accessValidator.validarAcessoFichaAmeaca(token, idFicha);

        Map<String, Object> camposValidados = validaCampos(request);
        repository.alterarFicha(idFicha, camposValidados);
    }

    @Override
    public void deletar(String token, String idFicha) throws ExecutionException, InterruptedException {
        accessValidator.validarAcessoFichaAmeaca(token, idFicha);
        repository.deletarFicha(idFicha);
    }

    @Override
    public void atualizarPericia(String token, String idFicha, PericiaUpdateDTO request) throws ExecutionException, InterruptedException {
        Ameaca ficha = accessValidator.validarAcessoFichaAmeaca(token, idFicha);

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
