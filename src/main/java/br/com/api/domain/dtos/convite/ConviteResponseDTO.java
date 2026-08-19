package br.com.api.domain.dtos.convite;

import br.com.api.domain.enums.StatusConvite;

public record ConviteResponseDTO(
        String token,
        String link,
        String nomeCampanha,
        StatusConvite status,
        String criadoEm,
        String expiraEm
) {}