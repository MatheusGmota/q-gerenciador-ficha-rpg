package br.com.api.domain.enums;

public enum TipoPatente {
    MUNDANO("Mundano"),
    RECRUTA("Recruta"),
    OPERADOR("Operador"),
    AGENTE_ESPECIAL("Agente Especial"),
    OFICIAL_DE_OPERACOES("Oficial de operacoes"),
    AGENTE_DE_ELITE("Agente de elite");

    private final String valor;

    TipoPatente(String valor) {
        this.valor = valor;
    }
}
