package br.com.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Atributos {
    private int agilidade;
    private int forca;
    private int intelecto;
    private int presenca;
    private int vigor;
}
