package br.com.api.domain.mappers;

import br.com.api.domain.dtos.user.CreateUserDTO;
import br.com.api.domain.dtos.user.UserResponseDTO;
import br.com.api.domain.entities.User;
import com.google.firebase.auth.UserRecord;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserMapper {

    public static User fromCreateUserDTO(CreateUserDTO dto) {
        User user = new User();
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setTelefone(dto.telefone());
        user.setPassword(dto.password());
        user.setUserRole(dto.userRole());
        return user;
    }

    public static UserResponseDTO fromUserRecord(UserRecord user) {
        return new UserResponseDTO(
                user.getUid(),
                user.getDisplayName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getPhotoUrl()
        );
    }
}
