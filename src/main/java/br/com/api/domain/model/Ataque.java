package br.com.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ataque {
    private String nome;
    private String teste;
    private String dano;

    private int critico;
    private String alcance;
    private String especial;
}
