package br.com.api.domain.entities.subcollections;

import br.com.api.domain.enums.StatusMembro;
import br.com.api.domain.enums.TipoMembro;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.ServerTimestamp;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MembroCampanha {
    @DocumentId
    private String idUsuario;

    private String nomeUsuario;
    private TipoMembro tipoMembro;
    private StatusMembro status;

    @ServerTimestamp
    private Timestamp entrouEm;
}
