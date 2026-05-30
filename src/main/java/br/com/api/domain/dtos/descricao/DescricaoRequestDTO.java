package br.com.api.domain.dtos.descricao;

public record DescricaoRequestDTO(
    String aparencia,
    String personalidade,
    String historico,
    String objetivo
){}
