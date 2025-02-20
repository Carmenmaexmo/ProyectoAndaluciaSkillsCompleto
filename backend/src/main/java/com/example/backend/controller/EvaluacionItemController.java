package com.example.backend.controller;

import com.example.backend.dto.EvaluacionItemDTO;
import com.example.backend.service.EvaluacionItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/evaluacionItems")
public class EvaluacionItemController {

    private final EvaluacionItemService evaluacionItemService;

    public EvaluacionItemController(EvaluacionItemService evaluacionItemService) {
        this.evaluacionItemService = evaluacionItemService;
    }

    @GetMapping
    public List<EvaluacionItemDTO> listar() {
        return evaluacionItemService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Optional<EvaluacionItemDTO> obtener(@PathVariable Integer id) {
        return evaluacionItemService.obtenerPorId(id);
    }

    @PostMapping
    public EvaluacionItemDTO agregar(@RequestBody EvaluacionItemDTO evaluacionItemDTO) {
        return evaluacionItemService.agregarEvaluacionItem(evaluacionItemDTO);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        evaluacionItemService.eliminarEvaluacionItem(id);
    }
}

