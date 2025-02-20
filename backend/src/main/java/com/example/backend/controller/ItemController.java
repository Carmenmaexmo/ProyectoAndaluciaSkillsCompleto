package com.example.backend.controller;

import com.example.backend.dto.ItemDTO;
import com.example.backend.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<ItemDTO> listar() {
        return itemService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Optional<ItemDTO> obtener(@PathVariable Integer id) {
        return itemService.obtenerPorId(id);
    }

    @PostMapping
    public ItemDTO agregar(@RequestBody ItemDTO itemDTO) {
        return itemService.agregarItem(itemDTO);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        itemService.eliminarItem(id);
    }
}
