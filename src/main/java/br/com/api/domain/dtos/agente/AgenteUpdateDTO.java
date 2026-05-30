package br.com.api.domain.dtos.agente;

import br.com.api.domain.dtos.atributos.AtributosRequestDTO;
import br.com.api.domain.dtos.descricao.DescricaoRequestDTO;
import br.com.api.domain.enums.TipoElemento;

public record AgenteUpdateDTO(
        String nome,
        String imagemUrl,

        int nivelExposicao,
        int defesa,
        String deslocamento,
        String resistencias,

        StatusDTO pontosVida,
        StatusDTO pontosSanidade,
        StatusDTO pontosEsforco,

        AtributosRequestDTO atributos,
        int idade,
        int esforcoPorRodada,
        TipoElemento afinidade,
        int defesaEsquiva,
        int reducaoBloqueio,
        String protecoes,
        DescricaoRequestDTO descricao
) {
}
