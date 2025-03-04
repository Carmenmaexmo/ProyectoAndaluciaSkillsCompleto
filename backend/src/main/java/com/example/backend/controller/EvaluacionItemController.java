package com.example.backend.controller;

import com.example.backend.dto.EvaluacionItemDTO;
import com.example.backend.service.EvaluacionItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/evaluacion-items")
@Tag(name = "Evaluacion Items", description = "API para gestionar items de evaluación")
public class EvaluacionItemController {

    private final EvaluacionItemService evaluacionItemService;

    public EvaluacionItemController(EvaluacionItemService evaluacionItemService) {
        this.evaluacionItemService = evaluacionItemService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los items de evaluación")
    public List<EvaluacionItemDTO> listar() {
        return evaluacionItemService.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un item de evaluación por ID")
    public Optional<EvaluacionItemDTO> obtener(@PathVariable Integer id) {
        return evaluacionItemService.obtenerPorId(id);
    }

    @GetMapping("/evaluacion/{evaluacionId}")
    @Operation(summary = "Obtener items de evaluación por ID de evaluación")
    public ResponseEntity<List<EvaluacionItemDTO>> obtenerEvaluacionItemsPorEvaluacion(@PathVariable Integer evaluacionId) {
        List<EvaluacionItemDTO> evaluacionItems = evaluacionItemService.obtenerEvaluacionItemsPorEvaluacion(evaluacionId);
        return ResponseEntity.ok(evaluacionItems);
    }

    @PostMapping
    @Operation(summary = "Agregar un nuevo item de evaluación")
    public EvaluacionItemDTO agregar(@RequestBody EvaluacionItemDTO evaluacionItemDTO) {
        return evaluacionItemService.agregarEvaluacionItem(evaluacionItemDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un item de evaluación")
    public EvaluacionItemDTO actualizar(@PathVariable Integer id, @RequestBody EvaluacionItemDTO evaluacionItemDTO) {
        return evaluacionItemService.actualizarEvaluacionItem(id, evaluacionItemDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un item de evaluación")
    public void eliminar(@PathVariable Integer id) {
        evaluacionItemService.eliminarEvaluacionItem(id);
    }
}
