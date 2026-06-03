package br.com.api.domain.dtos.inventario;

import br.com.api.domain.enums.CategoriaItem;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record ItemRequestDTO(
        @NotNull
        String nome,

        @NotNull
        CategoriaItem categoria,

        @NotNull
        Integer espacos,

        @NotNull
        String descricao,

        @Valid
        AtaqueRequestDTO ataque
){
}

