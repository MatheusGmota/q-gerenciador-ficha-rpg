package br.com.api.domain.mappers;

import br.com.api.domain.dtos.fichavinculada.FichaVinculadaResponseDTO;
import br.com.api.domain.entities.subcollections.FichaVinculada;
import org.mapstruct.Mapper;

@Mapper
public interface FichaVinculadaMapper {

    FichaVinculadaResponseDTO toDto(FichaVinculada vinculo);
}