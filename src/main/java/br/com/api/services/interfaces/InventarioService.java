package br.com.api.services.interfaces;

import br.com.api.domain.dtos.inventario.InventarioUpdateDTO;
import br.com.api.domain.dtos.inventario.InventarioResponseDTO;
import br.com.api.domain.dtos.inventario.ItemRequestDTO;
import br.com.api.domain.dtos.inventario.ItemResponseDTO;
import br.com.api.domain.entities.Inventario;

import java.util.concurrent.ExecutionException;

public interface InventarioService {
    Inventario inicializar(String idInventario) throws ExecutionException, InterruptedException;
    InventarioResponseDTO obterOuCriar(String idInventario) throws ExecutionException, InterruptedException;
    void atualizar(String idInventario, InventarioUpdateDTO request) throws ExecutionException, InterruptedException;

    // ========== ITENS ==========
    ItemResponseDTO obterItemPorId(String idInventario, String idItem) throws ExecutionException, InterruptedException;
    ItemResponseDTO adicionarItem(String idInventario, ItemRequestDTO request) throws ExecutionException, InterruptedException;
    void atualizarItem(String idInventario, String idItem, ItemRequestDTO request) throws ExecutionException, InterruptedException;
    void deletarItem(String idFicha, String idItem) throws ExecutionException, InterruptedException;
}
