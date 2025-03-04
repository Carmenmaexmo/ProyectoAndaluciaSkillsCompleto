package com.example.backend.controller;

import com.example.backend.dto.ItemDTO;
import com.example.backend.service.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/items")
@Tag(name = "Items", description = "API para gestionar los items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los items")
    public List<ItemDTO> listar() {
        return itemService.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un item por ID")
    public Optional<ItemDTO> obtener(@PathVariable Integer id) {
        return itemService.obtenerPorId(id);
    }

    @PostMapping
    @Operation(summary = "Agregar un nuevo item")
    public ItemDTO agregar(@RequestBody ItemDTO itemDTO) {
        return itemService.agregarItem(itemDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un item")
    public void eliminar(@PathVariable Integer id) {
        itemService.eliminarItem(id);
    }
}
