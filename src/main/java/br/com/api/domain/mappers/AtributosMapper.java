package br.com.api.domain.mappers;

import br.com.api.domain.dtos.atributos.AtributosRequestDTO;
import br.com.api.domain.model.Atributos;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AtributosMapper {

    public static Atributos toAtributos(AtributosRequestDTO dto) {
        return new Atributos(
                dto.agilidade(),
                dto.forca(),
                dto.intelecto(),
                dto.presenca(),
                dto.vigor()
        );
    }
}
