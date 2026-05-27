package br.com.api.domain.enums;

public enum UserRole {
    ADMIN("ADMIN"),
    USER("USER");

    private final String valor;

    UserRole(String valor) {
        this.valor = valor;
    }
}
