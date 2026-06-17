package br.com.api.domain.dtos.ritual;

import br.com.api.domain.model.CustoRitual;

public record RitualResponseDTO(
        String id,
        String nome,
        String tipoElemento,
        CustoRitual custoRitual,
        String execucao,
        String alcance,
        String alvo,
        String duracao,
        String resistencia,
        String descricao,
        int dtRitual,
        String danoSanidade,
        int circulo
) {
}
