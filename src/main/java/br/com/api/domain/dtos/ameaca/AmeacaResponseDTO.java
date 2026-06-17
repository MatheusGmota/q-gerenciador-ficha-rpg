package br.com.api.domain.dtos.ameaca;

import br.com.api.domain.enums.TipoElemento;
import br.com.api.domain.enums.TipoTamanho;
import br.com.api.domain.model.Atributos;
import br.com.api.domain.model.Pericia;
import br.com.api.domain.model.Status;

import java.util.Map;

public record AmeacaResponseDTO(
        String id,

        String idUsuario,
        String imagemUrl,
        String nome,
        Integer nivelExposicao,
        Integer defesa,
        String deslocamento,
        String resistencias,

        Status pontosVida,
        Atributos atributos,
        Map<String, Pericia> pericias,

        Integer vd,
        String tipo,
        TipoTamanho tamanho,
        String descricao,

        TipoElemento elementoPrimario,
        String elementosSecundarios,

        String vulnerabilidades,
        String especial,
        String imunidades,
        Integer machucado,
        String enigmaDoMedo,

        String dt,
        String danoMental,
        String acoes,
        String criadoEm
) {
}
