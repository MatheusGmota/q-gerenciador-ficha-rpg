package br.com.api.domain.factories;

import br.com.api.domain.dtos.campanha.CampanhaCreateDTO;
import br.com.api.domain.entities.Campanha;
import br.com.api.domain.entities.subcollections.MembroCampanha;
import br.com.api.domain.enums.StatusCampanha;
import br.com.api.domain.enums.StatusMembro;
import br.com.api.domain.enums.TipoMembro;
import br.com.api.domain.mappers.CampanhaMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CampanhaFactory {

    @Inject
    CampanhaMapper mapper;

    public Campanha criar(String idUsuario, CampanhaCreateDTO dto) {
        Campanha campanha = mapper.toCampanha(dto);
        campanha.setIdMestre(idUsuario);
        campanha.setStatus(StatusCampanha.ATIVA);
        campanha.setMaxMembros(10);
        return campanha;
    }

    public MembroCampanha inicializarMestre(String uid, String nomeUsuario) {
        MembroCampanha membro = new MembroCampanha();
        membro.setTipoMembro(TipoMembro.MESTRE);
        membro.setStatus(StatusMembro.ATIVO);
        membro.setNomeUsuario(nomeUsuario);
        return membro;
    }
}
