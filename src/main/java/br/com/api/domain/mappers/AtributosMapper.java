package br.com.api.domain.mappers;

import br.com.api.domain.dtos.atributos.AtributosRequestDTO;
import br.com.api.domain.model.Atributos;
import org.mapstruct.Mapper;

@Mapper()
public interface AtributosMapper {

    Atributos toAtributos(AtributosRequestDTO dto);
}
