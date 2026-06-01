package br.com.api.domain.entities.subcollections;

import br.com.api.annotation.FirestoreCollection;
import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FirestoreCollection("habilidades")
public class Habilidade {

    @DocumentId
    private String id;

    private String nome;
    private String descricao;
}