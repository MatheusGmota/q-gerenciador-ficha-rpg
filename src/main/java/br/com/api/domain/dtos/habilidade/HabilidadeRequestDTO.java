package br.com.api.domain.dtos.habilidade;

import jakarta.validation.constraints.NotNull;

public record HabilidadeRequestDTO (
        @NotNull
        String nome,

        @NotNull
        String descricao
) {
}
