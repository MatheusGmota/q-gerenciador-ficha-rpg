package br.com.api.domain.dtos.campanha;

public record CampanhaResumoResponseDTO(
        String id,
        String nomeCampanha,
        String imagemCapaUrl,
        String criadoEm
) {
}
