package br.com.api.domain.model;

import br.com.api.domain.enums.CategoriaItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LimiteItens {
    private Map<CategoriaItem, Integer> limites;
}
