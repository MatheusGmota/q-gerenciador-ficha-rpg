package br.com.api.domain.entities.subcollections;

import br.com.api.domain.enums.TipoAcao;
import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Acao {

    @DocumentId
    private String id;

    private TipoAcao acao;
    private String nome;

    private String execucao;
    private String alcance;
    private String teste;
    private String dano;
}
