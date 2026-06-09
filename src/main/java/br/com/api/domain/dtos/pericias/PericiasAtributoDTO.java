package br.com.api.domain.dtos.pericias;

import java.util.List;

public record PericiasAtributoDTO (
        List<PericiaDTO> agilidade,
        List<PericiaDTO> forca,
        List<PericiaDTO> intelecto,
        List<PericiaDTO> presenca,
        List<PericiaDTO> vigor
) {}