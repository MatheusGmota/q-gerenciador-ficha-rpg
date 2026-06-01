package br.com.api.domain.mappers;

import br.com.api.domain.dtos.atributos.AtributosRequestDTO;
import br.com.api.domain.model.Atributos;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface AtributosMapper {

    Atributos toAtributos(AtributosRequestDTO dto);
}
