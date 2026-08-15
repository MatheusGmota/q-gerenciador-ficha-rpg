package br.com.api.domain.dtos.membro;

import br.com.api.domain.enums.StatusMembro;
import br.com.api.domain.enums.TipoMembro;

public record MembroResponseDTO(
        String idUsuario,
        String nomeUsuario,
        TipoMembro tipoMembro,
        StatusMembro status,
        String entrouEm
)  {
}
