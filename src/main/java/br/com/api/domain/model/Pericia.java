package br.com.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pericia {

    private boolean treinado;
    private int testeBase;
    private int bonus;
    private String bonusDescricao;
}
