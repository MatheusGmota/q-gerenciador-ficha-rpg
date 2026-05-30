package br.com.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Descricao {
    private String aparencia;
    private String personalidade;
    private String objetivo;
    private String historico;
}
