package br.com.api.domain.mappers;

import br.com.api.domain.dtos.membro.MembroResponseDTO;
import br.com.api.domain.entities.subcollections.MembroCampanha;
import com.google.cloud.Timestamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface MembroMapper {

    @Mapping(target = "entrouEm", source = "entrouEm", qualifiedByName = "timestampToString")
    MembroResponseDTO toMembroDto(MembroCampanha membroCampanha);

    @Named("timestampToString")
    default String timestampToString(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }

        return timestamp.toSqlTimestamp().toString();
    }
}
