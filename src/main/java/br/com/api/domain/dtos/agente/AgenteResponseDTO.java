package br.com.api.domain.dtos.agente;

import br.com.api.domain.enums.TipoClasse;
import br.com.api.domain.enums.TipoElemento;
import br.com.api.domain.enums.TipoOrigem;
import br.com.api.domain.enums.TipoTrilha;
import br.com.api.domain.model.Atributos;
import br.com.api.domain.model.Status;
import com.google.cloud.Timestamp;

public record AgenteResponseDTO (
        String id,
        String idUsuario,

        String imagemUrl,
        String nome,
        int idade,
        TipoOrigem origem,
        TipoClasse classe,
        TipoTrilha trilha,
        TipoElemento afinidade,

        Status pontosVida,
        Status pontosSanidade,
        Status pontosEsforco,
        Atributos atributos,

        int nivelExposicao,
        int esforcoPorRodada,
        int defesa,
        int defesaEsquiva,
        int reducaoBloqueio,
        String deslocamento,
        String resistencias,
        String protecoes,

        Timestamp criadoEm
) {
}
