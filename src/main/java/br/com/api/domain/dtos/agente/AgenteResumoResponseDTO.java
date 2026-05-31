package br.com.api.domain.dtos.agente;


public record AgenteResumoResponseDTO(
        String id,
        String imagemUrl,
        String nome,
        String criadoEm
) {
}
