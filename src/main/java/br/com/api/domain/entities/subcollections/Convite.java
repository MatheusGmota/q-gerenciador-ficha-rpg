package br.com.api.domain.entities.subcollections;

import br.com.api.domain.enums.StatusConvite;
import com.google.cloud.Timestamp;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Convite {

    private String nomeCampanha;
    private String token;
    private StatusConvite status;
    private Timestamp criadoEm;
}