package br.com.api.domain.mappers;

import br.com.api.domain.dtos.agente.AgenteCreateDTO;
import br.com.api.domain.dtos.agente.AgenteResponseDTO;
import br.com.api.domain.dtos.agente.AgenteResumoResponseDTO;
import br.com.api.domain.entities.Agente;
import com.google.cloud.Timestamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI, uses = { AtributosMapper.class, DescricaoMapper.class })
public interface AgenteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idUsuario", source = "uid")
    @Mapping(target = "imagemUrl", ignore = true)

    @Mapping(target = "nivelExposicao", ignore = true)
    @Mapping(target = "defesa", ignore = true)
    @Mapping(target = "defesaEsquiva", ignore = true)

    @Mapping(target = "reducaoBloqueio", ignore = true)
    @Mapping(target = "esforcoPorRodada", ignore = true)
    @Mapping(target = "deslocamento", ignore = true)

    @Mapping(target = "pontosVida", ignore = true)
    @Mapping(target = "pontosEsforco", ignore = true)
    @Mapping(target = "pontosSanidade", ignore = true)
    Agente toAgente(String uid, AgenteCreateDTO dto);

    @Mapping(target = "criadoEm", source = "criadoEm", qualifiedByName = "timestampToString")
    AgenteResponseDTO toAgenteDto(Agente agente);

    @Mapping(target = "criadoEm", source = "criadoEm", qualifiedByName = "timestampToString")
    AgenteResumoResponseDTO toAgenteResumoDto(Agente a);

    @Named("timestampToString")
    default String timestampToString(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }

        return timestamp.toSqlTimestamp().toString();
    }
}
