package br.com.api.domain.entities.subcollections;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Habilidade {
    public static final String COLLECTION_NAME = "habilidades";

    @DocumentId
    private String id;

    private String nome;
    private String descricao;
}