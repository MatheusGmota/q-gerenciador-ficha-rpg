package br.com.api.services;

import br.com.api.domain.dtos.inventario.InventarioResponseDTO;
import br.com.api.domain.dtos.inventario.InventarioUpdateDTO;
import br.com.api.domain.dtos.inventario.ItemRequestDTO;
import br.com.api.domain.dtos.inventario.ItemResponseDTO;
import br.com.api.domain.entities.Inventario;
import br.com.api.domain.entities.subcollections.Item;
import br.com.api.domain.factories.InventarioFactory;
import br.com.api.domain.mappers.InventarioMapper;
import br.com.api.repositories.ItemRepositoryImpl;
import br.com.api.repositories.interfaces.InventarioRepository;
import br.com.api.services.interfaces.InventarioService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static br.com.api.services.validators.ValidationUtil.validaCampos;

@ApplicationScoped
public class InventarioServiceImpl extends AbstractSubcollectionService implements InventarioService {

    @Inject
    InventarioMapper mapper;

    @Inject
    InventarioFactory inventarioFactory;

    @Inject
    InventarioRepository repository;

    @Inject
    ItemRepositoryImpl itemRepository;

    @Override
    public Inventario inicializar(String idInventario) throws ExecutionException, InterruptedException {
        Inventario inicializar = inventarioFactory.inicializar();
        return repository.persistir(idInventario, inicializar);
    }

    @Override
    public InventarioResponseDTO obterOuCriar(String token, String idInventario) throws ExecutionException, InterruptedException {
       validarAcessoFicha(token, idInventario);
        Inventario inventario = repository.obterPorId(idInventario);

        if (inventario == null) {
            inventario = inicializar(idInventario);
        }

        List<Item> items = itemRepository.procurarTudo(idInventario);
        inventario.setItens(items);

        return mapper.toInventarioDto(inventario);
    }

    @Override
    public void atualizar(String token, String idInventario, InventarioUpdateDTO request) throws ExecutionException, InterruptedException {
        validarAcessoFicha(token, idInventario);

        Map<String, Object> camposValidados = validaCampos(request);

        repository.alterar(idInventario, camposValidados);
    }

    // ========== ITEM ==========
    @Override
    public ItemResponseDTO obterItemPorId(String token, String idInventario, String idItem) throws ExecutionException, InterruptedException {
        validarAcessoFicha(token, idInventario);

        Item item = itemRepository.procurarPorId(idInventario, idItem)
                .orElseThrow(() -> new NotFoundException("Item não encontrada"));

        return mapper.toItemDto(item);
    }

    @Override
    public ItemResponseDTO adicionarItem(String token, String idInventario, ItemRequestDTO request) throws ExecutionException, InterruptedException {
        validarAcessoFicha(token, idInventario);

        Item item = itemRepository.persistir(idInventario, mapper.toItem(request));

        return mapper.toItemDto(item);
    }

    @Override
    public void atualizarItem(String token, String idInventario, String idItem, ItemRequestDTO request) throws ExecutionException, InterruptedException {
        validarAcessoFicha(token, idInventario);
        itemRepository.procurarPorId(idInventario, idItem)
                .orElseThrow(() -> new NotFoundException("Item não encontrado"));

        Map<String, Object> camposValidados = validaCampos(request);

        itemRepository.atualizar(idInventario, idItem, camposValidados);
    }

    @Override
    public void deletarItem(String token, String idInventario, String idItem) throws ExecutionException, InterruptedException {
        validarAcessoFicha(token, idInventario);

        itemRepository.procurarPorId(idInventario, idItem)
                .orElseThrow(() -> new NotFoundException("Item não encontrado"));

        itemRepository.remover(idInventario, idItem);
    }
}
