package br.com.api.domain.entities;

import br.com.api.domain.enums.LimiteCredito;
import br.com.api.domain.enums.TipoPatente;
import br.com.api.domain.entities.subcollections.Item;
import br.com.api.domain.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventario {
    private Status carga;
    private int pontosPrestigio;
    private TipoPatente patente;
    private List<Item> itens;
    private LimiteCredito limiteCreditos;
    private Map<String, Integer> limitesItens;

}
