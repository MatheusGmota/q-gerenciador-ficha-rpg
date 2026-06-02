package br.com.api.domain.entities.subcollections;

import br.com.api.annotation.FirestoreCollection;
import br.com.api.domain.enums.TipoElemento;
import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FirestoreCollection("rituais")
public class Ritual {

    @DocumentId
    private String id;

    private String nome;

    private String alcance;
    private String alvo;
    private int circulo;
    private String danoSanidade;
    private String descricao;
    private int dtRitual;
    private String duracao;
    private String execucao;
    private String resistencia;
    private TipoElemento tipoElemento;

}
