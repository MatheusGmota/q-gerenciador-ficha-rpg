package br.com.api.domain.dtos.atributos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AtributosRequestDTO(
    @NotNull
    @Min(0)
    Integer agilidade,

    @NotNull
    @Min(0)
    Integer forca,

    @NotNull
    @Min(0)
    Integer intelecto,

    @NotNull
    @Min(0)
    Integer presenca,

    @NotNull
    @Min(0)
    Integer vigor
){}
