package br.com.api.domain.dtos.fichavinculada;

import br.com.api.domain.enums.TipoFicha;
import com.google.cloud.Timestamp;

public record FichaVinculadaResponseDTO(
        String idFicha,
        String idUsuario,
        String nomeUsuario,
        String nomeFicha,
        TipoFicha tipo,
        Timestamp vinculadoEm
) {}