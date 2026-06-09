package br.com.api.domain.enums;

import lombok.Getter;

@Getter
public enum TipoPericia {

    ACROBACIA(TipoAtributo.AGILIDADE),
    CRIME(TipoAtributo.AGILIDADE),
    FURTIVIDADE(TipoAtributo.AGILIDADE),
    INICIATIVA(TipoAtributo.AGILIDADE),
    PILOTAGEM(TipoAtributo.AGILIDADE),
    PONTARIA(TipoAtributo.AGILIDADE),
    REFLEXOS(TipoAtributo.AGILIDADE),

    ATLETISMO(TipoAtributo.FORCA),
    LUTA(TipoAtributo.FORCA),

    ATUALIDADES(TipoAtributo.INTELECTO),
    CIENCIAS(TipoAtributo.INTELECTO),
    INVESTIGACAO(TipoAtributo.INTELECTO),
    MEDICINA(TipoAtributo.INTELECTO),
    OCULTISMO(TipoAtributo.INTELECTO),
    PROFISSAO(TipoAtributo.INTELECTO),
    SOBREVIVENCIA(TipoAtributo.INTELECTO),
    TATICA(TipoAtributo.INTELECTO),
    TECNOLOGIA(TipoAtributo.INTELECTO),

    ADESTRAMENTO(TipoAtributo.PRESENCA),
    ARTES(TipoAtributo.PRESENCA),
    DIPLOMACIA(TipoAtributo.PRESENCA),
    ENGANACAO(TipoAtributo.PRESENCA),
    INTIMIDACAO(TipoAtributo.PRESENCA),
    INTUICAO(TipoAtributo.PRESENCA),
    PERCEPCAO(TipoAtributo.PRESENCA),
    RELIGIAO(TipoAtributo.PRESENCA),
    VONTADE(TipoAtributo.PRESENCA),

    FORTITUDE(TipoAtributo.VIGOR);

    private final TipoAtributo atributo;

    TipoPericia(TipoAtributo atributo) {
        this.atributo = atributo;
    }

}