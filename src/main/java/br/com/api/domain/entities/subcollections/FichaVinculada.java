package br.com.api.domain.entities.subcollections;

import br.com.api.domain.enums.TipoFicha;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.Exclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FichaVinculada {
    @Exclude
    private String idFicha;

    private String idUsuario;
    private String nomeUsuario;
    private String nomeFicha;
    private TipoFicha tipo;
    private Timestamp vinculadoEm;
}
