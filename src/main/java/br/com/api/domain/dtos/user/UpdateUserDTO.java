package br.com.api.domain.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateUserDTO(
        @Size(min = 3, max = 50, message = "Username deve ter entre 3 e 50 caracteres")
        String username,

        @Email(message = "E-mail inválido")
        String email,

        String telefone,
        String photoUrl
) {
}
