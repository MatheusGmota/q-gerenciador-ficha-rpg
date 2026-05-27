package br.com.api.services.interfaces;

import br.com.api.domain.dtos.user.*;
import com.google.firebase.auth.FirebaseAuthException;

public interface UserService {
    UserResponseDTO getUser(String uid) throws FirebaseAuthException;
    UserResponseDTO getLoggedUser(String token) throws FirebaseAuthException;
    ResetPasswordLink resetPassword(String token, String email) throws FirebaseAuthException;
    TokenResponseDTO addUser(CreateUserDTO dto) throws FirebaseAuthException, InterruptedException;
    void updateUser(String token, UpdateUserDTO dto) throws FirebaseAuthException;
    void removeUser(String token) throws FirebaseAuthException;
    TokenResponseDTO login(LoginDTO dto) throws FirebaseAuthException, InterruptedException;
}
