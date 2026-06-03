package br.com.api.domain.mappers;

import br.com.api.domain.dtos.user.CreateUserDTO;
import br.com.api.domain.dtos.user.UserResponseDTO;
import br.com.api.domain.entities.User;
import com.google.firebase.auth.UserRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper()
public interface UserMapper {

    @Mapping(target = "uid", ignore = true)
    @Mapping(target = "photoUrl", ignore = true)
    User fromCreateUserDTO(CreateUserDTO dto);

    @Mapping(target = "username", source = "user.displayName")
    @Mapping(target = "telefone",  source = "user.phoneNumber")
    @Mapping(target = "userRole", source = "userRole")
    UserResponseDTO fromUserRecord(UserRecord user, String userRole);
}
