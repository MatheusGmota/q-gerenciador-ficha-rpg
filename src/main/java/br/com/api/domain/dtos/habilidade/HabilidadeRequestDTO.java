package br.com.api.domain.dtos.habilidade;

import jakarta.validation.constraints.NotNull;

public record HabilidadeRequestDTO (
        @NotNull(message = "Nome é obrigatório")
        String nome,

        @NotNull(message = "Descrição é obrigatório")
        String descricao
) {
}
