package br.com.api.domain.entities;

import br.com.api.domain.enums.TipoClasse;
import br.com.api.domain.enums.TipoElemento;
import br.com.api.domain.enums.TipoOrigem;
import br.com.api.domain.enums.TipoTrilha;
import br.com.api.domain.model.Descricao;
import br.com.api.domain.model.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
public class Agente extends Ficha {
    private int idade;
    private int esforcoPorRodada;
    private TipoOrigem origem;
    private TipoClasse classe;
    private TipoTrilha trilha;
    private TipoElemento afinidade;
    private int defesaEsquiva;
    private int reducaoBloqueio;
    private String protecoes;

    private Status pontosSanidade;
    private Status pontosEsforco;
    private Descricao descricao;
}
