package br.com.api.domain.dtos.pericias;

import br.com.api.domain.enums.TipoPericia;

public record PericiaUpdateDTO(
        TipoPericia nome,
        boolean treinado,
        Integer testeBase,
        Integer bonus,
        String bonusDescricao
) {

}
