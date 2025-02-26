package com.example.backend.controller;

import com.example.backend.dto.EvaluacionItemDTO;
import com.example.backend.service.EvaluacionItemService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/evaluacion-items")
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

    @GetMapping("/evaluacion/{evaluacionId}")
    public ResponseEntity<List<EvaluacionItemDTO>> obtenerEvaluacionItemsPorEvaluacion(@PathVariable Integer evaluacionId) {
        List<EvaluacionItemDTO> evaluacionItems = evaluacionItemService.obtenerEvaluacionItemsPorEvaluacion(evaluacionId);
        return ResponseEntity.ok(evaluacionItems);
    }

    @PostMapping
    public EvaluacionItemDTO agregar(@RequestBody EvaluacionItemDTO evaluacionItemDTO) {
        return evaluacionItemService.agregarEvaluacionItem(evaluacionItemDTO);
    }

    @PutMapping("/{id}")
    public EvaluacionItemDTO actualizar(@PathVariable Integer id, @RequestBody EvaluacionItemDTO evaluacionItemDTO) {
        return evaluacionItemService.actualizarEvaluacionItem(id, evaluacionItemDTO);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        evaluacionItemService.eliminarEvaluacionItem(id);
    }
}