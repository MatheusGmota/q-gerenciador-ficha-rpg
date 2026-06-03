package br.com.api.domain.mappers;

import br.com.api.domain.dtos.inventario.InventarioResponseDTO;
import br.com.api.domain.dtos.inventario.ItemRequestDTO;
import br.com.api.domain.dtos.inventario.ItemResponseDTO;
import br.com.api.domain.entities.Inventario;
import br.com.api.domain.entities.subcollections.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface InventarioMapper {

    InventarioResponseDTO toInventarioDto(Inventario inventario);

    // ======= ITEM =======
    @Mapping(target = "id", ignore = true)
    Item toItem(ItemRequestDTO item);

    ItemResponseDTO toItemDto(Item item);
}
