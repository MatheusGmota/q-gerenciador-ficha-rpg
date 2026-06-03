package br.com.api.domain.entities.subcollections;

import br.com.api.annotation.FirestoreCollection;
import br.com.api.domain.enums.CategoriaItem;
import br.com.api.domain.model.Ataque;
import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FirestoreCollection("itens")
public class Item {
    @DocumentId
    private String id;

    private String nome;
    private CategoriaItem categoria;
    private int espacos;
    private String descricao;
    private Ataque ataque;
}
