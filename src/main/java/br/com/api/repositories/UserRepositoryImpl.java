package br.com.api.repositories;

import br.com.api.domain.dtos.user.UpdateUserDTO;
import br.com.api.domain.entities.User;
import br.com.api.infra.providers.FirebaseProvider;
import br.com.api.repositories.interfaces.UserRepository;
import br.com.api.services.AuthenticationService;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class UserRepositoryImpl implements UserRepository {

    @Inject
    AuthenticationService authService;

    @Inject
    FirebaseProvider provider;

    public UserRecord criarUsuario(User user) throws FirebaseAuthException {

        UserRecord.CreateRequest request =
                new UserRecord.CreateRequest()
                        .setEmail(user.getEmail())
                        .setPassword(user.getPassword())
                        .setDisplayName(user.getUsername());

        if (user.getTelefone() != null && !user.getTelefone().isBlank()) {
            request.setPhoneNumber(user.getTelefone());
        }

        return provider.getFirebaseAuth().createUser(request);
    }

    public void atualizarUsuario(String uid, UpdateUserDTO dto) throws FirebaseAuthException {
        UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(uid);
        boolean houveMudanca = false;

        if (dto.email() != null && !dto.email().isBlank()) {
            request.setEmail(dto.email());
            houveMudanca = true;
        }

        if (dto.username() != null && !dto.username().isBlank()) {
            request.setDisplayName(dto.username());
            houveMudanca = true;
        }

        if (dto.telefone() != null && !dto.telefone().isBlank()) {
            request.setPhoneNumber(dto.telefone());
            houveMudanca = true;
        }

        if (dto.photoUrl() != null && !dto.photoUrl().isBlank()) {
            request.setPhotoUrl(dto.photoUrl());
            houveMudanca = true;
        }

        if (houveMudanca) {
            provider.getFirebaseAuth().updateUser(request);
            log.info("Usuário atualizado: uid={}", uid);
        }

        authService.updateUserClaims(uid, dto.username());
    }

    public String obterLinkResetSenha(String email) throws FirebaseAuthException {
        return provider.getFirebaseAuth().generatePasswordResetLink(email);
    }

    @Override
    public UserRecord obterPorId(String uid) throws FirebaseAuthException {
        return provider.getFirebaseAuth().getUser(uid);
    }

    public UserRecord obterPorEmail(String email) throws FirebaseAuthException {
        return provider.getFirebaseAuth().getUserByEmail(email);
    }

    public UserRecord obterUsuarioLogado(String token) throws FirebaseAuthException {
        String uid = authService.validarToken(token).getUid();
        return provider.getFirebaseAuth().getUser(uid);
    }

    public void deletarUsuario(String uid) throws FirebaseAuthException {
        provider.getFirebaseAuth().deleteUser(uid);
        log.info("Usuário deletado: uid={}", uid);
    }
}
