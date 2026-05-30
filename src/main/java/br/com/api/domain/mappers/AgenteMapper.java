package br.com.api.domain.mappers;

import br.com.api.domain.dtos.agente.AgenteCreateDTO;
import br.com.api.domain.dtos.agente.AgenteResponseDTO;
import br.com.api.domain.entities.Agente;
import br.com.api.domain.enums.TipoClasse;
import br.com.api.domain.model.Atributos;
import br.com.api.domain.model.Status;
import lombok.NoArgsConstructor;

import static br.com.api.domain.mappers.AtributosMapper.toAtributos;
import static br.com.api.domain.mappers.DescricaoMapper.toDescricao;

@NoArgsConstructor
public class AgenteMapper {

    public static Agente toAgente(String uid, AgenteCreateDTO dto) {
        Atributos atributos = toAtributos(dto.atributos());
        Status pontosVida = calcularPontosVida(atributos.getVigor(), dto.classe());
        Status pontosEsforco = calcularPontosEsforco(atributos.getPresenca(), dto.classe());
        Status pontosSanidade = calcularPontosSanidade(dto.classe());
        int defesa = calcularDefesa(atributos.getAgilidade());
        int nivelExposicao = setNivelExposicao(dto.classe());

        return Agente.builder()
                .idUsuario(uid)
                .nome(dto.nome())
                .idade(dto.idade())
                .origem(dto.origem())
                .classe(dto.classe())
                .trilha(dto.trilha())
                .descricao(toDescricao(dto.descricao()))
                .atributos(atributos)
                .nivelExposicao(nivelExposicao)
                .defesa(defesa)
                .defesaEsquiva(defesa)
                .reducaoBloqueio(0)
                .esforcoPorRodada(calcularEsforcoPorRodada(nivelExposicao))
                .deslocamento("9m/6q")
                .pontosVida(pontosVida)
                .pontosEsforco(pontosEsforco)
                .pontosSanidade(pontosSanidade)
                .build();
    }

    private static int setNivelExposicao(TipoClasse classe) {
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

    public static AgenteResponseDTO toAgenteDto(Agente a) {
        return new AgenteResponseDTO(
                a.getId(),
                a.getIdUsuario(),

                a.getImagemUrl(),
                a.getNome(),
                a.getIdade(),
                a.getOrigem(),
                a.getClasse(),
                a.getTrilha(),
                a.getAfinidade(),

                a.getPontosVida(),
                a.getPontosSanidade(),
                a.getPontosEsforco(),
                a.getAtributos(),

                a.getNivelExposicao(),
                a.getEsforcoPorRodada(),
                a.getDefesa(),
                a.getDefesaEsquiva(),
                a.getReducaoBloqueio(),
                a.getDeslocamento(),
                a.getResistencias(),
                a.getProtecoes(),
                a.getCriadoEm()
        );
    }
}
