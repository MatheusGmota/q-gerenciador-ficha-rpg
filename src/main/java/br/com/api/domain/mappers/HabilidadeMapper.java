package br.com.api.domain.mappers;

import br.com.api.domain.dtos.habilidade.HabilidadeRequestDTO;
import br.com.api.domain.dtos.habilidade.HabilidadeResponseDTO;
import br.com.api.domain.entities.subcollections.Habilidade;

public class HabilidadeMapper {

    public static Habilidade toHabilidade(HabilidadeRequestDTO dto) {
        Habilidade habilidade = new Habilidade();
        habilidade.setNome(dto.nome());
        habilidade.setDescricao(dto.descricao());

        return habilidade;
    }

    public static HabilidadeResponseDTO toHabilidadeDto(Habilidade h) {
        return new HabilidadeResponseDTO(
                h.getId(),
                h.getNome(),
                h.getDescricao()
        );
    }
}
