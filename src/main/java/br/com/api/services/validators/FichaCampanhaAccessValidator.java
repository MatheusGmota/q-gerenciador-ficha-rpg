package br.com.api.services.validators;

import br.com.api.domain.entities.subcollections.MembroCampanha;
import br.com.api.domain.enums.TipoMembro;
import br.com.api.repositories.interfaces.CampanhaRepository;
import br.com.api.repositories.interfaces.MembroRepository;
import br.com.api.services.AuthenticationService;
import com.google.firebase.auth.FirebaseToken;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;

import java.util.concurrent.ExecutionException;

@ApplicationScoped
public class FichaCampanhaAccessValidator {

    @Inject
    AuthenticationService authService;

    @Inject
    CampanhaRepository campanhaRepository;

    @Inject
    MembroRepository membroRepository;

    public record ContextoAcesso(String uid, MembroCampanha membro) {
        public boolean ehMestre() {
            return membro.getTipoMembro() == TipoMembro.MESTRE;
        }
    }

    public ContextoAcesso autenticarMembro(String uid, String idCampanha) throws ExecutionException, InterruptedException {
        campanhaRepository.obterPorId(idCampanha)
                .orElseThrow(() -> new NotFoundException("Campanha não encontrada"));

        MembroCampanha membro = membroRepository.obterPorCampanhaEUsuario(idCampanha, uid)
                .orElseThrow(() -> new ForbiddenException("Usuário não possui acesso a esta campanha"));

        return new ContextoAcesso(uid, membro);
    }

    public ContextoAcesso exigirMestre(String uid, String idCampanha) throws ExecutionException, InterruptedException {
        ContextoAcesso ctx = autenticarMembro(uid, idCampanha);

        if (!ctx.ehMestre()) {
            throw new ForbiddenException("Apenas o mestre da campanha pode realizar esta ação");
        }

        return ctx;
    }
}