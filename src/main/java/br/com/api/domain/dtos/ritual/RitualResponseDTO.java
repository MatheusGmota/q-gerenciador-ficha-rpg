package br.com.api.domain.dtos.ritual;

public record RitualResponseDTO(
        String id,
        String nome,
        String alcance,
        String alvo,
        int circulo,
        String danoSanidade,
        String descricao,
        int dtRitual,
        String duracao,
        String execucao,
        String resistencia,
        String tipoElemento
) {
}
