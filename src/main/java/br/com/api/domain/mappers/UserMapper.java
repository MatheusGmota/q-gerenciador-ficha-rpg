package br.com.api.domain.mappers;

import br.com.api.domain.dtos.user.CreateUserDTO;
import br.com.api.domain.dtos.user.UserResponseDTO;
import br.com.api.domain.entities.User;
import com.google.firebase.auth.UserRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface UserMapper {

    User fromCreateUserDTO(CreateUserDTO dto);

    UserResponseDTO fromUserRecord(UserRecord user);
}
