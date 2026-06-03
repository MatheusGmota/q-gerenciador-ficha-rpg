package br.com.api.domain.dtos.inventario;

import jakarta.validation.constraints.NotNull;

public record AtaqueRequestDTO(
        @NotNull
        String nome,

        @NotNull
        String teste,

        @NotNull
        String dano,

        @NotNull
        Integer critico,

        @NotNull
        String alcance,

        @NotNull
        String especial
) {
}
