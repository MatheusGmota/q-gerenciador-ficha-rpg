package br.com.api.domain.mappers;

import br.com.api.domain.dtos.descricao.DescricaoRequestDTO;
import br.com.api.domain.model.Descricao;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DescricaoMapper {

    public static Descricao toDescricao(DescricaoRequestDTO dto) {
        return new Descricao(
                dto.aparencia(),
                dto.personalidade(),
                dto.objetivo(),
                dto.historico()
        );
    }
}
