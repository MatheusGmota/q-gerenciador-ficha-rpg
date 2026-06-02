package br.com.api.domain.mappers;

import br.com.api.domain.dtos.ritual.RitualRequestDTO;
import br.com.api.domain.dtos.ritual.RitualResponseDTO;
import br.com.api.domain.entities.subcollections.Ritual;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface RitualMapper {

//    RitualMapper INSTANCE = Mappers.getMapper(RitualMapper.class);

    @Mapping(target = "danoSanidade", source = "danoSanidade", qualifiedByName = "gerarTextoDanoSanidade")
    Ritual toRitual(RitualRequestDTO dto);

    RitualResponseDTO toRitualDto(Ritual ritual);

    @Named("gerarTextoDanoSanidade")
    default String gerarTextoDanoSanidade(String danoSanidade) {
        return "Fazer um teste de ocultismo (DT = 20+qtd de PE gastos). Se falhar, toma o dano de sanidade igual ao número de PE gastos para fazer o ritual.";
    }

}
