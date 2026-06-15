package br.com.api.domain.mappers;

import br.com.api.domain.dtos.ameaca.AmeacaResponseDTO;
import br.com.api.domain.dtos.ameaca.AmeacaResumoResponseDTO;
import br.com.api.domain.dtos.ameaca.AmeacaUpdateDTO;
import br.com.api.domain.dtos.pericias.PericiaUpdateDTO;
import br.com.api.domain.entities.Ameaca;
import br.com.api.domain.model.Pericia;
import com.google.cloud.Timestamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface AmeacaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idUsuario", source = "uid")
    @Mapping(target = "criadoEm", ignore = true)
    Ameaca toAmeaca(String uid, AmeacaUpdateDTO dto);

    @Mapping(target = "criadoEm", source = "criadoEm", qualifiedByName = "timestampToString")
    AmeacaResponseDTO toAmeacaDto(Ameaca agente);

    @Mapping(target = "criadoEm", source = "criadoEm", qualifiedByName = "timestampToString")
    AmeacaResumoResponseDTO toAmeacaResumoDto(Ameaca a);

    @Mapping(target = "dto.nome", ignore = true)
    Pericia toPericia(PericiaUpdateDTO dto);

    @Named("timestampToString")
    default String timestampToString(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        
        return timestamp.toSqlTimestamp().toString();
    }
}
