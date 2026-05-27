package br.com.api.repositories.interfaces;

import br.com.api.domain.dtos.user.UpdateUserDTO;
import br.com.api.domain.entities.User;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

public interface UserRepository {
    String obterLinkResetSenha(String email) throws FirebaseAuthException;
    UserRecord obterPorId(String uid) throws FirebaseAuthException;
    UserRecord obterPorEmail(String email) throws FirebaseAuthException;
    UserRecord obterUsuarioLogado(String token) throws FirebaseAuthException;
    UserRecord criarUsuario(User dto) throws FirebaseAuthException;
    void atualizarUsuario(String uid, UpdateUserDTO dto) throws FirebaseAuthException;
    void deletarUsuario(String uid) throws FirebaseAuthException;
}
