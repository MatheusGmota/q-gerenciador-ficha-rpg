package br.com.api.domain.dtos.ameaca;

public record AmeacaResumoResponseDTO(
        String id,
        String imagemUrl,
        String nome,
        String criadoEm
) {
}
