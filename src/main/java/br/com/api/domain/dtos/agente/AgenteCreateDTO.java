package br.com.api.domain.dtos.agente;

import br.com.api.domain.dtos.atributos.AtributosRequestDTO;
import br.com.api.domain.dtos.descricao.DescricaoRequestDTO;
import br.com.api.domain.enums.TipoClasse;
import br.com.api.domain.enums.TipoOrigem;
import br.com.api.domain.enums.TipoTrilha;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record AgenteCreateDTO (

    @NotBlank
    String nome,

    @NotNull
    @Min(0)
    Integer idade,

    @NotNull
    TipoOrigem origem,

    @NotNull
    TipoClasse classe,

    // Opcional para MUNDANO, obrigatório para demais classes
    TipoTrilha trilha,

    @Valid
    DescricaoRequestDTO descricao,

    @Valid
    @NotNull
    AtributosRequestDTO atributos
) {
    @JsonIgnore
    @AssertTrue(message = "Trilha inválida para a classe")
    boolean isTrilhaValida() {
        // já validado pelo @NotNull
        if (classe == null) {
            return true;
        }

        // Mundano não precisa trilha
        if (classe == TipoClasse.MUNDANO) {
            return trilha == null;
        }

        // Demais classes precisam trilha
        if (trilha == null) {
            return false;
        }

        return trilha.pertenceAClasse(classe);
    }
}
