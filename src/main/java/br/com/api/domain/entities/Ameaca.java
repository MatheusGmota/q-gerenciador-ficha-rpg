package br.com.api.domain.entities;

import br.com.api.domain.enums.TipoElemento;
import br.com.api.domain.enums.TipoTamanho;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
public class Ameaca extends Ficha {
    private int vd;
    private String tipo; // relíquia, criatura, pessoa, animal
    private TipoTamanho tamanho;
    private String descricao;

    private TipoElemento elementoPrimario;
    private String elementosSecundarios;

    private String vulnerabilidades;
    private String especial;
    private String imunidades;
    private int machucado;
    private String enigmaDoMedo;

    private String dt;
    private String danoMental;
    private String acoes;
}
