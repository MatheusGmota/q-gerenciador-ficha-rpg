package br.com.api.services.interfaces;

import br.com.api.domain.dtos.ritual.RitualRequestDTO;
import br.com.api.domain.dtos.ritual.RitualResponseDTO;

public interface RitualService extends FichaSubcollectionService<RitualResponseDTO, RitualRequestDTO> {}
