package br.com.api.domain.entities;

import br.com.api.domain.model.Atributos;
import br.com.api.domain.model.Status;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.ServerTimestamp;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class Ficha {

    @DocumentId
    private String id;

    private String idUsuario;
    private String imagemUrl;
    private String nome;
    private int nivelExposicao;
    private int defesa;
    private String deslocamento;
    private String resistencias;

    private Status pontosVida;
    private Atributos atributos;

    @ServerTimestamp
    private Timestamp criadoEm;
}
