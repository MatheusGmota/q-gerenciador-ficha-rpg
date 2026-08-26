package br.com.api.domain.dtos.campanha;

import br.com.api.domain.enums.StatusCampanha;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record CampanhaUpdateDTO(

        @Size(min = 1, max = 100, message = "O nome deve ter entre 1 e 100 caracteres")
        String nome,

        @Size(max = 1000, message = "A descrição deve ter no máximo 1000 caracteres")
        String descricao,

        String imagemCapaUrl,

        StatusCampanha status,

        @Min(value = 1, message = "O limite de fichas deve ser pelo menos 1")
        Integer maxFichas
) {
}