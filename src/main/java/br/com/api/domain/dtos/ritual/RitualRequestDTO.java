package br.com.api.domain.dtos.ritual;

import br.com.api.domain.enums.TipoElemento;
import br.com.api.domain.model.CustoRitual;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RitualRequestDTO(
        @NotNull(message = "Nome é obrigatório")
        String nome,

        @NotNull(message = "TipoElemento é obrigatório")
        TipoElemento tipoElemento,

        CustoRitual custoRitual,

        @NotNull(message = "Execucao é obrigatório")
        String execucao,

        @NotNull(message = "Alcance é obrigatório")
        String alcance,

        @NotNull(message = "Alvo é obrigatório")
        String alvo,

        @NotNull(message = "Duração é obrigatório")
        String duracao,

        @NotNull(message = "Resistência é obrigatório")
        String resistencia,

        @NotNull(message = "Descrição é obrigatório")
        String descricao,

        @Min(0)
        @NotNull(message = "dtRitual é obrigatório")
        Integer dtRitual,

        String danoSanidade,

        @Max(4)
        @Min(1)
        @NotNull(message = "Círculo é obrigatório")
        Integer circulo
) {
}
