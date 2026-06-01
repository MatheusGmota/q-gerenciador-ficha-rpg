package br.com.api.services.interfaces;

import br.com.api.domain.dtos.habilidade.HabilidadeRequestDTO;
import br.com.api.domain.dtos.habilidade.HabilidadeResponseDTO;

public interface HabilidadeService extends FichaSubcollectionService<HabilidadeResponseDTO, HabilidadeRequestDTO> {}
