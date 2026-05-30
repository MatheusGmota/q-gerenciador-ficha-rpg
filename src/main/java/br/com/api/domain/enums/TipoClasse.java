package br.com.api.domain.enums;

public enum TipoClasse {
    COMBATENTE("Combatente"),
    ESPECIALISTA("Especialista"),
    OCULTISTA("Ocultista"),
    MUNDANO("Mundano");

    private final String valor;

    TipoClasse(String valor) {
        this.valor = valor;
    }
}
