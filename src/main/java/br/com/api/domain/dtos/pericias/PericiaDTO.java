package br.com.api.domain.dtos.pericias;

public record PericiaDTO(
        String nome,
        boolean treinado,
        int testeBase,
        int bonus,
        String bonusDescricao
) {
}
