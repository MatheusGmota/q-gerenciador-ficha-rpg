package br.com.api.domain.entities;

import br.com.api.domain.enums.StatusCampanha;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.ServerTimestamp;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Campanha {

    @DocumentId
    private String id;

    private String idMestre;
    private String nome;
    private String descricao;
    private String imagemCapaUrl;
    private int maxMembros;
    private StatusCampanha status;

    @ServerTimestamp
    private Timestamp criadoEm;
}
