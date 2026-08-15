package br.com.api.domain.dtos.campanha;

import br.com.api.domain.enums.StatusCampanha;

public record CampanhaResponseDTO(
        String id,
        String idMestre,
        String nome,
        String descricao,
        String imagemCapaUrl,
        Integer maxMembros,
        StatusCampanha status,
        String criadoEm
) {
}
