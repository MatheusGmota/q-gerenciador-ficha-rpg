package br.com.api.domain.mappers;

import br.com.api.domain.dtos.agente.AgenteCreateDTO;
import br.com.api.domain.dtos.agente.AgenteResponseDTO;
import br.com.api.domain.dtos.agente.AgenteResumoResponseDTO;
import br.com.api.domain.dtos.pericias.PericiaDTO;
import br.com.api.domain.dtos.pericias.PericiaUpdateDTO;
import br.com.api.domain.dtos.pericias.PericiasAtributoDTO;
import br.com.api.domain.entities.Agente;
import br.com.api.domain.enums.TipoAtributo;
import br.com.api.domain.model.Pericia;
import com.google.cloud.Timestamp;
import org.mapstruct.*;

import java.util.List;
import java.util.Map;

@Mapper(uses = { AtributosMapper.class, DescricaoMapper.class })
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

    @Mapping(target = "resistencias", ignore = true)
    @Mapping(target = "afinidade", ignore = true)
    @Mapping(target = "protecoes", ignore = true)

    @Mapping(target = "pericias", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    Agente toAgente(String uid, AgenteCreateDTO dto);

    @Mapping(target = "criadoEm", source = "criadoEm", qualifiedByName = "timestampToString")
    AgenteResponseDTO toAgenteDto(Agente agente);

    @Mapping(target = "criadoEm", source = "criadoEm", qualifiedByName = "timestampToString")
    AgenteResumoResponseDTO toAgenteResumoDto(Agente a);

    @Mapping(target = "dto.nome", ignore = true)
    Pericia toPericia(PericiaUpdateDTO dto);

    default PericiasAtributoDTO toPericiasAtributoDto(Map<TipoAtributo, List<PericiaDTO>> agrupadas) {

        return new PericiasAtributoDTO(
                agrupadas.getOrDefault(TipoAtributo.AGILIDADE, List.of()),
                agrupadas.getOrDefault(TipoAtributo.FORCA, List.of()),
                agrupadas.getOrDefault(TipoAtributo.INTELECTO, List.of()),
                agrupadas.getOrDefault(TipoAtributo.PRESENCA, List.of()),
                agrupadas.getOrDefault(TipoAtributo.VIGOR, List.of())
        );
    }

    @Named("timestampToString")
    default String timestampToString(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }

        return timestamp.toSqlTimestamp().toString();
    }
}
