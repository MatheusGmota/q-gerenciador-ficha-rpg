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
        limites.put(CategoriaItem.I.toString(),   2);
        limites.put(CategoriaItem.II.toString(),  0);
        limites.put(CategoriaItem.III.toString(), 0);
        limites.put(CategoriaItem.IV.toString(),  0);
        limites.put(CategoriaItem.V.toString(),   0);
        limites.put(CategoriaItem.VI.toString(),  0);
        return limites;
    }
}
