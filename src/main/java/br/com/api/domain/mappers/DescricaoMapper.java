package br.com.api.domain.mappers;

import br.com.api.domain.dtos.descricao.DescricaoRequestDTO;
import br.com.api.domain.model.Descricao;
import org.mapstruct.Mapper;

@Mapper()
public interface DescricaoMapper {

    Descricao toDescricao(DescricaoRequestDTO dto);
}
