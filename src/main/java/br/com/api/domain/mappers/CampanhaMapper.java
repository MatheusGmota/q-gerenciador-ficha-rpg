package br.com.api.domain.mappers;

import br.com.api.domain.dtos.campanha.CampanhaCreateDTO;
import br.com.api.domain.dtos.campanha.CampanhaResponseDTO;
import br.com.api.domain.dtos.campanha.CampanhaResumoResponseDTO;
import br.com.api.domain.dtos.membro.MembroResponseDTO;
import br.com.api.domain.entities.Campanha;
import br.com.api.domain.entities.subcollections.MembroCampanha;
import com.google.cloud.Timestamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface CampanhaMapper {

    @Mapping(target = "id",  ignore = true)
    @Mapping(target = "idMestre",  ignore = true)
    @Mapping(target = "imagemCapaUrl",  ignore = true)
    @Mapping(target = "maxMembros",  ignore = true)
    @Mapping(target = "status",  ignore = true)
    @Mapping(target = "criadoEm",  ignore = true)
    Campanha toCampanha(CampanhaCreateDTO dto);

    @Mapping(target = "criadoEm", source = "criadoEm", qualifiedByName = "timestampToString")
    CampanhaResponseDTO toCampanhaDto(Campanha campanha);

    @Mapping(target = "nomeCampanha", source = "nome")
    @Mapping(target = "criadoEm", source = "criadoEm", qualifiedByName = "timestampToString")
    CampanhaResumoResponseDTO toCampanhaResumoDto(Campanha campanha);

    @Mapping(target = "entrouEm", source = "entrouEm", qualifiedByName = "timestampToString")
    MembroResponseDTO toMembroDto(MembroCampanha membro);

    @Named("timestampToString")
    default String timestampToString(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }

        return timestamp.toSqlTimestamp().toString();
    }
}
