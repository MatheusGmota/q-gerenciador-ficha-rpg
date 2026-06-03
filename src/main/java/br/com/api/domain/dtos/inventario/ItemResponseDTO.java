package br.com.api.domain.dtos.inventario;

import br.com.api.domain.enums.CategoriaItem;
import br.com.api.domain.model.Ataque;

public record ItemResponseDTO(
        String id,
        String nome,
        CategoriaItem categoria,
        int espacos,
        String descricao,
        Ataque ataque
) {
}
