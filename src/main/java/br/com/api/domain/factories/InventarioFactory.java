package br.com.api.domain.factories;

import br.com.api.domain.entities.Inventario;
import br.com.api.domain.enums.CategoriaItem;
import br.com.api.domain.enums.LimiteCredito;
import br.com.api.domain.enums.TipoPatente;
import br.com.api.domain.model.Status;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class InventarioFactory {

    public Inventario inicializar() {
        Inventario inventario = new Inventario();

        inventario.setCarga(new Status(0, 5));

        inventario.setPontosPrestigio(0);

        inventario.setPatente(TipoPatente.RECRUTA);

        inventario.setItens(new ArrayList<>());

        inventario.setLimiteCreditos(LimiteCredito.BAIXO);

        inventario.setLimitesItens(inicializarLimiteItens());

        return inventario;
    }

    private Map<String, Integer> inicializarLimiteItens(){
        Map<String, Integer> limites = new HashMap<>();
        for (CategoriaItem catItem : CategoriaItem.values()) {
            String catItemName = catItem.name().toLowerCase();

            if (catItem.name().equals(CategoriaItem.I.name())) {
                limites.put(catItemName, 2);
            }

            else limites.put(catItemName, 0);
        }

        return limites;
    }
}
