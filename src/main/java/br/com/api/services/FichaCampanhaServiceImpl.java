package br.com.api.services;

import br.com.api.domain.dtos.agente.AgenteResponseDTO;
import br.com.api.domain.dtos.agente.AgenteUpdateDTO;
import br.com.api.domain.dtos.ameaca.AmeacaResponseDTO;
import br.com.api.domain.dtos.ameaca.AmeacaUpdateDTO;
import br.com.api.domain.dtos.fichavinculada.FichaVinculadaResponseDTO;
import br.com.api.domain.entities.Agente;
import br.com.api.domain.entities.Ameaca;
import br.com.api.domain.entities.subcollections.FichaVinculada;
import br.com.api.domain.enums.TipoFicha;
import br.com.api.domain.mappers.AgenteMapper;
import br.com.api.domain.mappers.AmeacaMapper;
import br.com.api.domain.mappers.FichaVinculadaMapper;
import br.com.api.infra.security.FirebaseUserPrincipal;
import br.com.api.repositories.interfaces.AgenteRepository;
import br.com.api.repositories.interfaces.AmeacaRepository;
import br.com.api.repositories.interfaces.FichaVinculadaRepository;
import br.com.api.services.interfaces.FichaCampanhaService;
import br.com.api.services.validators.FichaCampanhaAccessValidator;
import br.com.api.services.validators.FichaCampanhaAccessValidator.ContextoAcesso;
import com.google.cloud.Timestamp;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static br.com.api.services.validators.ValidationUtil.validaCampos;

@ApplicationScoped
public class FichaCampanhaServiceImpl implements FichaCampanhaService {

    @Inject
    FichaCampanhaAccessValidator accessValidator;

    @Inject
    FichaVinculadaRepository fichaVinculadaRepository;

    @Inject
    AgenteRepository agenteRepository;

    @Inject
    AmeacaRepository ameacaRepository;

    @Inject
    AgenteMapper agenteMapper;

    @Inject
    AmeacaMapper ameacaMapper;

    @Inject
    FichaVinculadaMapper fichaVinculadaMapper;

    @Inject
    FirebaseUserPrincipal currentUser;

    @Override
    public FichaVinculadaResponseDTO vincularAgente(String idCampanha, String idFicha) throws ExecutionException, InterruptedException {
        String uid = currentUser.getUid();
        ContextoAcesso ctx = accessValidator.autenticarMembro(uid, idCampanha);

        Agente ficha = agenteRepository.obterPorId(idFicha)
                .orElseThrow(() -> new NotFoundException("Ficha não encontrada"));

        if (!ficha.getIdUsuario().equals(ctx.uid())) {
            throw new ForbiddenException("Você só pode vincular fichas de sua propriedade");
        }

        FichaVinculada vinculo = montarVinculo(ctx, ficha.getId(), ficha.getNome(), TipoFicha.AGENTE);
        FichaVinculada persistido = fichaVinculadaRepository.vincularComValidacao(idCampanha, vinculo, ctx.ehMestre());

        return fichaVinculadaMapper.toDto(persistido);
    }

    @Override
    public FichaVinculadaResponseDTO vincularAmeaca(String idCampanha, String idFicha) throws ExecutionException, InterruptedException {
        String uid = currentUser.getUid();
        ContextoAcesso ctx = accessValidator.exigirMestre(uid, idCampanha);

        Ameaca ficha = ameacaRepository.obterPorId(idFicha)
                .orElseThrow(() -> new NotFoundException("Ficha não encontrada"));

        if (!ficha.getIdUsuario().equals(ctx.uid())) {
            throw new ForbiddenException("Você só pode vincular fichas de sua propriedade");
        }

        FichaVinculada vinculo = montarVinculo(ctx, ficha.getId(), ficha.getNome(), TipoFicha.AMEACA);
        FichaVinculada persistido = fichaVinculadaRepository.vincularComValidacao(idCampanha, vinculo, true);

        return fichaVinculadaMapper.toDto(persistido);
    }

    @Override
    public void desvincularAgente(String idCampanha, String idFicha) throws ExecutionException, InterruptedException {
        String uid = currentUser.getUid();
        ContextoAcesso ctx = accessValidator.autenticarMembro(uid, idCampanha);

        FichaVinculada vinculo = fichaVinculadaRepository.obterPorId(idCampanha, idFicha)
                .orElseThrow(() -> new NotFoundException("Ficha não está vinculada a esta campanha"));

        if (!ctx.ehMestre() && !vinculo.getIdUsuario().equals(ctx.uid())) {
            throw new ForbiddenException("Você não pode desvincular fichas de outro usuário");
        }

        fichaVinculadaRepository.desvincular(idCampanha, idFicha);
    }

    @Override
    public void desvincularAmeaca(String idCampanha, String idFicha) throws ExecutionException, InterruptedException {
        String uid = currentUser.getUid();
        accessValidator.exigirMestre(uid, idCampanha);

        fichaVinculadaRepository.obterPorId(idCampanha, idFicha)
                .orElseThrow(() -> new NotFoundException("Ficha não está vinculada a esta campanha"));

        fichaVinculadaRepository.desvincular(idCampanha, idFicha);
    }

    @Override
    public List<FichaVinculadaResponseDTO> listarFichas(String idCampanha) throws ExecutionException, InterruptedException {
        String uid = currentUser.getUid();
        ContextoAcesso ctx = accessValidator.autenticarMembro(uid, idCampanha);

        List<FichaVinculada> vinculos = ctx.ehMestre()
                ? fichaVinculadaRepository.obterTodas(idCampanha)
                : fichaVinculadaRepository.obterPorUsuario(idCampanha, ctx.uid());

        return vinculos.stream().map(fichaVinculadaMapper::toDto).toList();
    }

    @Override
    public AgenteResponseDTO obterAgente(String idCampanha, String idFicha) throws ExecutionException, InterruptedException {
        String uid = currentUser.getUid();
        ContextoAcesso ctx = accessValidator.autenticarMembro(uid, idCampanha);

        FichaVinculada vinculo = validarVinculoAgente(idCampanha, idFicha, ctx);

        Agente ficha = agenteRepository.obterPorId(vinculo.getIdFicha())
                .orElseThrow(() -> new NotFoundException("Ficha não encontrada"));

        return agenteMapper.toAgenteDto(ficha);
    }

    @Override
    public void atualizarAgente(String idCampanha, String idFicha, AgenteUpdateDTO request) throws ExecutionException, InterruptedException {
        String uid = currentUser.getUid();
        ContextoAcesso ctx = accessValidator.autenticarMembro(uid, idCampanha);

        validarVinculoAgente(idCampanha, idFicha, ctx);

        Map<String, Object> camposValidados = validaCampos(request);
        agenteRepository.alterarFicha(idFicha, camposValidados);
    }

    @Override
    public AmeacaResponseDTO obterAmeaca(String idCampanha, String idFicha) throws ExecutionException, InterruptedException {
        String uid = currentUser.getUid();
        accessValidator.exigirMestre(uid, idCampanha);

        fichaVinculadaRepository.obterPorId(idCampanha, idFicha)
                .orElseThrow(() -> new NotFoundException("Ficha não vinculada a esta campanha"));

        Ameaca ficha = ameacaRepository.obterPorId(idFicha)
                .orElseThrow(() -> new NotFoundException("Ficha não encontrada"));

        return ameacaMapper.toAmeacaDto(ficha);
    }

    @Override
    public void atualizarAmeaca(String idCampanha, String idFicha, AmeacaUpdateDTO request) throws ExecutionException, InterruptedException {
        String uid = currentUser.getUid();
        accessValidator.exigirMestre(uid, idCampanha);

        fichaVinculadaRepository.obterPorId(idCampanha, idFicha)
                .orElseThrow(() -> new NotFoundException("Ficha não vinculada a esta campanha"));

        Map<String, Object> camposValidados = validaCampos(request);
        ameacaRepository.alterarFicha(idFicha, camposValidados);
    }

    private FichaVinculada validarVinculoAgente(String idCampanha, String idFicha, ContextoAcesso ctx) throws ExecutionException, InterruptedException {
        FichaVinculada vinculo = fichaVinculadaRepository.obterPorId(idCampanha, idFicha)
                .orElseThrow(() -> new NotFoundException("Ficha não vinculada a esta campanha"));

        if (!ctx.ehMestre() && !vinculo.getIdUsuario().equals(ctx.uid())) {
            throw new ForbiddenException("Você não pode acessar fichas de outro usuário nesta campanha");
        }

        return vinculo;
    }

    private FichaVinculada montarVinculo(ContextoAcesso ctx, String idFicha, String nomeFicha, TipoFicha tipo) {
        FichaVinculada vinculo = new FichaVinculada();
        vinculo.setIdFicha(idFicha);
        vinculo.setIdUsuario(ctx.uid());
        vinculo.setNomeUsuario(ctx.membro().getNomeUsuario());
        vinculo.setNomeFicha(nomeFicha);
        vinculo.setTipo(tipo);
        vinculo.setVinculadoEm(Timestamp.now());
        return vinculo;
    }
}