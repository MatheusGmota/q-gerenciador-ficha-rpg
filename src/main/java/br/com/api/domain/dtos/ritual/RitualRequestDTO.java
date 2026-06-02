package br.com.api.domain.dtos.ritual;

import br.com.api.domain.enums.TipoElemento;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RitualRequestDTO(
        @NotNull(message = "Nome é obrigatório")
        String nome,

        @NotNull(message = "Nome é obrigatório")
        String alcance,

        @NotNull(message = "Nome é obrigatório")
        String alvo,

        @Min(1)
        @Max(4)
        @NotNull(message = "Nome é obrigatório")
        int circulo,

        @NotNull(message = "Nome é obrigatório")
        String danoSanidade,

        @NotNull(message = "Nome é obrigatório")
        String descricao,

        @Min(0)
        @NotNull(message = "Nome é obrigatório")
        int dtRitual,

        @NotNull(message = "Nome é obrigatório")
        String duracao,

        @NotNull(message = "Nome é obrigatório")
        String execucao,

        @NotNull(message = "Nome é obrigatório")
        String resistencia,

        @NotNull(message = "Nome é obrigatório")
        TipoElemento tipoElemento
) {
}
