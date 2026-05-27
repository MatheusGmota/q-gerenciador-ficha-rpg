package br.com.api.domain.dtos.user;

public record UserResponseDTO(
        String uid,
        String username,
        String email,
        String telefone,
        String photoUrl
) {
}
