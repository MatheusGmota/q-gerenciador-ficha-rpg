package br.com.api.domain.mappers;

import br.com.api.domain.dtos.habilidade.HabilidadeRequestDTO;
import br.com.api.domain.dtos.habilidade.HabilidadeResponseDTO;
import br.com.api.domain.entities.subcollections.Habilidade;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper()
public interface HabilidadeMapper {


    @Mapping(target = "id", ignore = true)
    Habilidade toHabilidade(HabilidadeRequestDTO dto);

    HabilidadeResponseDTO toHabilidadeDto(Habilidade h);
}
