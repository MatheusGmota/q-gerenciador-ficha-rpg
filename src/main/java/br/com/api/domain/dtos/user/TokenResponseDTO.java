package br.com.api.domain.dtos.user;

import br.com.api.domain.enums.UserRole;

public record TokenResponseDTO(
        String uid,
        String username,
        String token,
        String refreshToken,
        UserRole userRole
) {
}
