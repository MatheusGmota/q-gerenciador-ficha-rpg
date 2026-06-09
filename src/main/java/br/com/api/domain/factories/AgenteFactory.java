package br.com.api.domain.factories;

import br.com.api.domain.dtos.agente.AgenteCreateDTO;
import br.com.api.domain.entities.Agente;
import br.com.api.domain.enums.TipoClasse;
import br.com.api.domain.enums.TipoPericia;
import br.com.api.domain.mappers.AgenteMapper;
import br.com.api.domain.model.Atributos;
import br.com.api.domain.model.Pericia;
import br.com.api.domain.model.Status;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class AgenteFactory {

    @Inject
    AgenteMapper mapper;

    public Agente criar(String uid, AgenteCreateDTO dto) {
        Agente agente = mapper.toAgente(uid, dto);
        Atributos atributos = agente.getAtributos();

        agente.setNivelExposicao(
                nivelExposicao(dto.classe())
        );

        agente.setDefesa(
                calcularDefesa(atributos.getAgilidade())
        );

        agente.setDefesaEsquiva(agente.getDefesa());

        agente.setReducaoBloqueio(0);

        agente.setDeslocamento("9m/6q");

        agente.setEsforcoPorRodada(
                calcularEsforcoPorRodada(agente.getNivelExposicao())
        );

        agente.setPontosVida(
                calcularPontosVida(atributos.getVigor(), dto.classe())
        );
        agente.setPontosEsforco(
                calcularPontosEsforco(atributos.getPresenca(), dto.classe())
        );
        agente.setPontosSanidade(
                calcularPontosSanidade(dto.classe())
        );

        agente.setPericias(iniciarPericias());

        return agente;
    }

    private static int nivelExposicao(TipoClasse classe) {
        if (classe.equals(TipoClasse.MUNDANO)) return 0; // classe MUNDANO iniciam com 0 de exposição paranormal
        return 5; // outras classes iniciam com 5
    }

    private static int calcularDefesa(int agilidade) {
        return agilidade + 10;
    }

    private static int calcularEsforcoPorRodada(int nivelExposicao) {
        return nivelExposicao / 5;
    }

    private static Status calcularPontosVida(int vigor, TipoClasse classe) {
        int total = vigor;

        if (classe.equals(TipoClasse.COMBATENTE)) total += 20;
        else if (classe.equals(TipoClasse.ESPECIALISTA)) total += 16;
        else if (classe.equals(TipoClasse.OCULTISTA)) total += 12;
        else if (classe.equals(TipoClasse.MUNDANO)) total += 8;

        return new Status(total, total);
    }

    private static Status calcularPontosEsforco(int presenca, TipoClasse classe) {
        int total = presenca;

        if (classe.equals(TipoClasse.COMBATENTE)) total += 2;
        else if (classe.equals(TipoClasse.ESPECIALISTA)) total += 3;
        else if (classe.equals(TipoClasse.OCULTISTA)) total += 4;
        else if (classe.equals(TipoClasse.MUNDANO)) total += 1;

        return new Status(total, total);
    }

    private static Status calcularPontosSanidade(TipoClasse classe) {
        int total = 0;

        if (classe.equals(TipoClasse.COMBATENTE)) total = 12;
        else if (classe.equals(TipoClasse.ESPECIALISTA)) total = 16;
        else if (classe.equals(TipoClasse.OCULTISTA)) total = 20;
        else if (classe.equals(TipoClasse.MUNDANO)) total = 8;

        return new Status(total, total);
    }

    private static Map<String, Pericia> iniciarPericias() {
        Map<String, Pericia> pericias = new HashMap<>();

        for (TipoPericia tipo : TipoPericia.values()) {
            pericias.put(
                    tipo.name().toLowerCase(),
                    new Pericia(
                            false,
                            0,
                            0,
                            null
                    )
            );
        }

        return pericias;
    }
}
