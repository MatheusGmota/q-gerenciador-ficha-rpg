package br.com.api.domain.dtos.agente;

import br.com.api.domain.dtos.atributos.AtributosRequestDTO;
import br.com.api.domain.dtos.descricao.DescricaoRequestDTO;
import br.com.api.domain.enums.TipoElemento;

public record AgenteUpdateDTO(
        String imagemUrl,
        String nome,
        int idade,

        StatusDTO pontosVida,
        StatusDTO pontosSanidade,
        StatusDTO pontosEsforco,

        AtributosRequestDTO atributos,
        DescricaoRequestDTO descricao,

        int nivelExposicao,
        int esforcoPorRodada,
        int defesa,
        int defesaEsquiva,
        int reducaoBloqueio,

        String protecoes,
        String resistencias,
        String deslocamento,

        TipoElemento afinidade
) {
}
