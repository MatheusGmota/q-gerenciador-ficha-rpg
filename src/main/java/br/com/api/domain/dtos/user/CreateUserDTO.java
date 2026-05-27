package br.com.api.domain.dtos.user;

import br.com.api.domain.enums.UserRole;
import jakarta.validation.constraints.*;

public record CreateUserDTO (
        @NotBlank(message = "Username é obrigatório")
        @Size(min = 3, max = 50, message = "Username deve ter entre 3 e 50 caracteres")
        String username,

        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,

        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
        String password,

        @Pattern(regexp = "^\\+?[0-9]{13}$")
        String telefone,

        @NotNull(message = "UserRole é obrigatório")
        UserRole userRole
) {
}
