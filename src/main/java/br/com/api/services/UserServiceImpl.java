package br.com.api.services;

import br.com.api.domain.dtos.user.*;
import br.com.api.domain.entities.User;
import br.com.api.domain.enums.UserRole;
import br.com.api.repositories.interfaces.UserRepository;
import br.com.api.services.interfaces.UserService;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import static br.com.api.domain.mappers.UserMapper.fromCreateUserDTO;
import static br.com.api.domain.mappers.UserMapper.fromUserRecord;

@Slf4j
@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    UserRepository repository;

    @Inject
    AuthenticationService authService;

    @Override
    public UserResponseDTO getUser(String uid) throws FirebaseAuthException {
        UserRecord user = repository.obterPorId(uid);
        return fromUserRecord(user);
    }

    @Override
    public UserResponseDTO getLoggedUser(String token) throws FirebaseAuthException {
        UserRecord userRecord = repository.obterUsuarioLogado(token);
        return fromUserRecord(userRecord);
    }

    @Override
    public TokenResponseDTO addUser(CreateUserDTO dto) throws FirebaseAuthException, InterruptedException {
        User user = fromCreateUserDTO(dto);

        UserRecord record = repository.criarUsuario(user);
        authService.setUserClaims(record.getUid(), user.getUsername(), user.getUserRole());

        return authService.loginComEmailESenha(
                user.getEmail(),
                user.getPassword(),
                user.getUsername(),
                user.getUserRole()
        );
    }

    @Override
    public ResetPasswordLink resetPassword(String token, String email) throws FirebaseAuthException {
        FirebaseToken firebaseToken = authService.validarToken(token);
        if (!firebaseToken.getEmail().equals(email)) {
            log.info("Reset Password Email: {} - User email: {}", email, firebaseToken.getEmail());
            throw new WebApplicationException("Email informado difere do usuário", Response.Status.BAD_REQUEST);
        }
        return new ResetPasswordLink(repository.obterLinkResetSenha(email));
    }

    @Override
    public void updateUser(String token, UpdateUserDTO dto) throws FirebaseAuthException {
        String uid = authService.validarToken(token).getUid(); // verifica existência do usuário
        repository.atualizarUsuario(uid, dto);
    }

    @Override
    public void removeUser(String token) throws FirebaseAuthException {
        String uid = authService.validarToken(token).getUid(); // verifica existência do usuário
        repository.deletarUsuario(uid);
    }

    @Override
    public TokenResponseDTO login(LoginDTO request) throws FirebaseAuthException, InterruptedException {
        UserRecord user = repository.obterPorEmail(request.email());

        Boolean isAdmin = (Boolean) user.getCustomClaims()
                .getOrDefault("admin", false);

        UserRole role = isAdmin ? UserRole.ADMIN : UserRole.USER;

        return authService.loginComEmailESenha(request.email(), request.password(), user.getDisplayName(), role);
    }
}
