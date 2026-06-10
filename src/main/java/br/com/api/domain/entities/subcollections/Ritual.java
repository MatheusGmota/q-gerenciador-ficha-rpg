package br.com.api.domain.entities.subcollections;

import br.com.api.annotation.FirestoreCollection;
import br.com.api.domain.enums.TipoElemento;
import br.com.api.domain.model.CustoRitual;
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
    private TipoElemento tipoElemento;
    private CustoRitual custoRitual;

    private String execucao;
    private String alcance;
    private String alvo;
    private String duracao;
    private String resistencia;
    private String descricao;
    private int dtRitual;
    private String danoSanidade;
    private int circulo;

}
