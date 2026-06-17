package br.com.api.domain.dtos.ficha;

public record FichaResumoResponseDTO(
        String id,
        String imagemUrl,
        String nome,
        String criadoEm
) {
}
