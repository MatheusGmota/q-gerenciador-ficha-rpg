package br.com.api.domain.dtos.inventario;

import br.com.api.domain.enums.CategoriaItem;
import br.com.api.domain.enums.LimiteCredito;
import br.com.api.domain.enums.TipoPatente;
import br.com.api.domain.entities.subcollections.Item;
import br.com.api.domain.model.Status;

import java.util.List;
import java.util.Map;

public record InventarioResponseDTO(
        Status carga,
        int pontosPrestigio,
        TipoPatente patente,
        List<Item> itens,
        LimiteCredito limiteCreditos,
        Map<CategoriaItem, Integer> limitesItens
) {
}
