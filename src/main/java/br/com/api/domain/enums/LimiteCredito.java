package br.com.api.domain.enums;

public enum LimiteCredito {
    BAIXO("Baixo"),
    MEDIO("Medio"),
    ALTO("Alto"),
    ILIMITADO("Ilimitado");

    private final String valor;

    LimiteCredito(String valor) {
        this.valor = valor;
    }
}
