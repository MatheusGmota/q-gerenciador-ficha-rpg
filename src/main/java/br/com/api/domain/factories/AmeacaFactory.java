package br.com.api.domain.factories;

import br.com.api.domain.entities.Ameaca;
import br.com.api.domain.enums.TipoPericia;
import br.com.api.domain.model.Atributos;
import br.com.api.domain.model.Pericia;
import br.com.api.domain.model.Status;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class AmeacaFactory {

    public Ameaca criar(String uid) {
        Ameaca ameaca = new Ameaca();
        ameaca.setIdUsuario(uid);
        ameaca.setNivelExposicao(0);
        ameaca.setDefesa(0);
        ameaca.setPontosVida(new Status(
                0,
                0
        ));
        ameaca.setAtributos(new Atributos(
                0,
                0,
                0,
                0,
                0
        ));
        ameaca.setPericias(iniciarPericias());
        ameaca.setDeslocamento("9m/6q");
        ameaca.setMachucado(0);
        ameaca.setDanoMental("?d?");
        ameaca.setAcoes("");
        return ameaca;
    }

    private Map<String, Pericia> iniciarPericias() {
        Pericia pericia = new Pericia(false, 0, 0 , null);
        Map<String, Pericia> map = new HashMap<>();

        map.put(TipoPericia.PERCEPCAO.name(), pericia);
        map.put(TipoPericia.INICIATIVA.name(), pericia);
        map.put(TipoPericia.FORTITUDE.name(), pericia);
        map.put(TipoPericia.REFLEXOS.name(), pericia);
        map.put(TipoPericia.VONTADE.name(), pericia);

        return map;
    }
}
