package br.com.api.domain.mappers;

import br.com.api.domain.dtos.convite.ConviteResponseDTO;
import br.com.api.domain.entities.subcollections.Convite;
import com.google.cloud.Timestamp;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.temporal.ChronoUnit;

@Mapper()
public abstract class ConviteMapper {

    public static final int VALIDADE_DIAS = 7;

    @Inject
    @ConfigProperty(name = "app.convite.base-url")
    String baseUrl;

    @Mapping(target = "link", source = "token", qualifiedByName = "formatarLink")
    @Mapping(target = "criadoEm", source = "criadoEm", qualifiedByName = "timestampToString")
    @Mapping(target = "expiraEm", source = "criadoEm", qualifiedByName = "calcularExpiracao")
    public abstract ConviteResponseDTO toConviteDto(Convite convite);

    @Named("formatarLink")
    String formatarLink(String token) {
        if (token == null) {
            return null;
        }
        return "%s/%s".formatted(baseUrl, token);
    }

    @Named("timestampToString")
    String timestampToString(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toSqlTimestamp().toString();
    }

    @Named("calcularExpiracao")
    String calcularExpiracao(Timestamp dataCriacao) {
        if (dataCriacao == null) {
            return null;
        }
        Timestamp expiracao = Timestamp.ofTimeSecondsAndNanos(
                dataCriacao.toDate().toInstant().plus(VALIDADE_DIAS, ChronoUnit.DAYS).getEpochSecond(),
                dataCriacao.getNanos()
        );
        return timestampToString(expiracao);
    }
}