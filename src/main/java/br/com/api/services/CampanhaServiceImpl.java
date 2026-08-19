package br.com.api.services;

import br.com.api.domain.dtos.campanha.CampanhaCreateDTO;
import br.com.api.domain.dtos.campanha.CampanhaResponseDTO;
import br.com.api.domain.dtos.campanha.CampanhaResumoResponseDTO;
import br.com.api.domain.dtos.campanha.CampanhaUpdateDTO;
import br.com.api.domain.dtos.convite.ConviteCreateDTO;
import br.com.api.domain.dtos.convite.ConviteResponseDTO;
import br.com.api.domain.dtos.convite.ResgatarConvite;
import br.com.api.domain.dtos.membro.MembroResponseDTO;
import br.com.api.domain.entities.Campanha;
import br.com.api.domain.entities.subcollections.Convite;
import br.com.api.domain.entities.subcollections.MembroCampanha;
import br.com.api.domain.enums.TipoMembro;
import br.com.api.domain.factories.CampanhaFactory;
import br.com.api.domain.factories.ConviteFactory;
import br.com.api.domain.mappers.CampanhaMapper;
import br.com.api.domain.mappers.ConviteMapper;
import br.com.api.domain.mappers.MembroMapper;
import br.com.api.repositories.interfaces.CampanhaRepository;
import br.com.api.repositories.interfaces.ConviteRepository;
import br.com.api.repositories.interfaces.MembroRepository;
import br.com.api.services.interfaces.CampanhaService;
import com.google.firebase.auth.FirebaseToken;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static br.com.api.services.validators.ValidationUtil.validaCampos;

@Slf4j
@ApplicationScoped
public class CampanhaServiceImpl implements CampanhaService {

    @Inject
    CampanhaMapper mapper;

    @Inject
    CampanhaFactory factory;

    @Inject
    CampanhaRepository repository;

    @Inject
    AuthenticationService authService;

    @Inject
    MembroRepository membroRepository;

    @Inject
    MembroMapper membroMapper;

    @Inject
    ConviteRepository conviteRepository;

    @Inject
    ConviteFactory conviteFactory;

    @Inject
    ConviteMapper conviteMapper;

    @Override
    public List<CampanhaResumoResponseDTO> obterTudo(String token) throws ExecutionException, InterruptedException {
        authService.validarToken(token);

        List<Campanha> campanhas = repository.obterTodas();

        return campanhas
                .stream().map(c -> mapper.toCampanhaResumoDto(c))
                .toList();
    }

    @Override
    public List<CampanhaResumoResponseDTO> obterPorIdUsuario(String token) throws ExecutionException, InterruptedException {
        String uid = authService.validarToken(token).getUid();

        List<Campanha> campanhas = repository.obterPorIdUsuario(uid);

        return campanhas
                .stream().map(c -> mapper.toCampanhaResumoDto(c))
                .toList();
    }

    @Override
    public CampanhaResponseDTO obter(String token, String idCampanha) throws ExecutionException, InterruptedException {
        authService.validarToken(token);

        Campanha campanha = repository.obterPorId(idCampanha)
                .orElseThrow(() -> new NotFoundException(
                        "Campanha '%s' não encontrada"
                                .formatted(idCampanha)
                ));

        return mapper.toCampanhaDto(campanha);
    }

    @Override
    public CampanhaResponseDTO criar(String token, CampanhaCreateDTO request) throws ExecutionException, InterruptedException {
        FirebaseToken firebaseToken = authService.validarToken(token);

        String uid = firebaseToken.getUid();

        Campanha campanha = repository.persistir(
                factory.criar(uid, request)
        );

        membroRepository.adicionar(
                campanha.getId(),
                uid,
                factory.inicializarMestre(uid, firebaseToken.getName())
        );

        return mapper.toCampanhaDto(campanha);
    }

    @Override
    public void atualizar(String token, String idCampanha, CampanhaUpdateDTO request) throws ExecutionException, InterruptedException {
        String uid = authService.validarToken(token).getUid();

        validarCampanhaExiste(idCampanha);
        validarAcessoMestre(idCampanha, uid);

        Map<String, Object> camposValidados = validaCampos(request);
        repository.alterar(idCampanha, camposValidados);
    }

    @Override
    public void deletar(String token, String idCampanha) throws ExecutionException, InterruptedException {
        String uid = authService.validarToken(token).getUid();

        validarCampanhaExiste(idCampanha);
        validarAcessoMestre(idCampanha, uid);

        membroRepository.deletarPorCampanha(idCampanha);
        repository.deletar(idCampanha);
    }

    @Override
    public List<MembroResponseDTO> obterMembros(String token, String idCampanha) throws ExecutionException, InterruptedException {
        authService.validarToken(token);

        validarCampanhaExiste(idCampanha);

        return membroRepository.obterTodosPorCampanha(idCampanha)
                .stream()
                .map(membroMapper::toMembroDto)
                .toList();
    }

    @Override
    public void removerMembro(String token, String idCampanha, String idUsuarioAlvo) throws ExecutionException, InterruptedException {
        String uid = authService.validarToken(token).getUid();

        validarCampanhaExiste(idCampanha);
        validarAcessoMestre(idCampanha, uid);

        if (uid.equals(idUsuarioAlvo)) {
            throw new ForbiddenException(
                    "O mestre não pode remover a si mesmo da campanha"
            );
        }

        membroRepository.obterPorCampanhaEUsuario(idCampanha, idUsuarioAlvo)
                .orElseThrow(() -> new NotFoundException(
                        "Membro '%s' não encontrado nesta campanha".formatted(idUsuarioAlvo)
                ));

        membroRepository.remover(idCampanha, idUsuarioAlvo);
    }

    @Override
    public ConviteResponseDTO gerarConvite(String token, String idCampanha, ConviteCreateDTO request) throws ExecutionException, InterruptedException {
        String uid = authService.validarToken(token).getUid();

        Campanha campanha = repository.obterPorId(idCampanha)
                .orElseThrow(() -> new NotFoundException(
                        "Campanha '%s' não encontrada".formatted(idCampanha)
                ));

        validarAcessoMestre(idCampanha, uid);

        Convite convite = conviteFactory.criar(campanha.getNome());
        conviteRepository.criar(idCampanha, convite);

        return conviteMapper.toConviteDto(convite);
    }

    @Override
    public MembroResponseDTO entrarPorConvite(String token, String tokenConvite) throws ExecutionException, InterruptedException {
        FirebaseToken firebaseToken = authService.validarToken(token);

        ResgatarConvite resultado = conviteRepository.resgatar(
                tokenConvite,
                firebaseToken.getUid(),
                firebaseToken.getName()
        );

        return membroMapper.toMembroDto(resultado.membro());
    }

    private void validarCampanhaExiste(String idCampanha) throws ExecutionException, InterruptedException {
        repository.obterPorId(idCampanha)
                .orElseThrow(() -> new NotFoundException(
                        "Campanha '%s' não encontrada".formatted(idCampanha)
                ));
    }

    private void validarAcessoMestre(String idCampanha, String uid) throws ExecutionException, InterruptedException {
        MembroCampanha membro = membroRepository.obterPorCampanhaEUsuario(idCampanha, uid)
                .orElseThrow(() -> new ForbiddenException(
                        "Usuário não possui acesso a esta campanha"
                ));

        if (membro.getTipoMembro() != TipoMembro.MESTRE) {
            throw new ForbiddenException(
                    "Apenas o mestre da campanha pode realizar esta ação"
            );
        }
    }
}
