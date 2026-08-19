package br.com.api.domain.dtos.convite;

import br.com.api.domain.entities.subcollections.MembroCampanha;

public record ResgatarConvite(String idCampanha, MembroCampanha membro) {}
