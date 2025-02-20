package com.example.backend.service.base;

import com.example.backend.dto.ItemDTO;
import java.util.List;
import java.util.Optional;

public interface ItemServiceBase {
    List<ItemDTO> obtenerTodos();
    Optional<ItemDTO> obtenerPorId(Integer id);
    ItemDTO agregarItem(ItemDTO itemDTO);
    void eliminarItem(Integer id);
}