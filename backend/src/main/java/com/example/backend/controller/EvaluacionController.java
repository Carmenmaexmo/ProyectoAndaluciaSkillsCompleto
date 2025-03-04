package com.example.backend.controller;

import com.example.backend.dto.EvaluacionDTO;
import com.example.backend.dto.PruebaDTO;
import com.example.backend.service.EvaluacionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/evaluaciones")
@Tag(name = "Evaluaciones", description = "API para gestionar evaluaciones")
public class EvaluacionController {

    private final EvaluacionService evaluacionService;

    public EvaluacionController(EvaluacionService evaluacionService) {
        this.evaluacionService = evaluacionService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las evaluaciones")
    public List<EvaluacionDTO> listar() {
        return evaluacionService.obtenerTodas();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una evaluación por ID")
    public Optional<EvaluacionDTO> obtener(@PathVariable Integer id) {
        return evaluacionService.obtenerPorId(id);
    }

    @GetMapping("/pruebas-por-participante")
    @Operation(summary = "Obtener pruebas por ID de participante")
    public ResponseEntity<List<PruebaDTO>> obtenerPruebasPorParticipante(@RequestParam Integer participanteId) {
        List<PruebaDTO> pruebas = evaluacionService.obtenerPruebasPorParticipante(participanteId);
        return ResponseEntity.ok(pruebas);
    }

    @GetMapping("/evaluacion-por-participante-y-prueba")
    @Operation(summary = "Obtener evaluación por participante y prueba")
    public ResponseEntity<EvaluacionDTO> obtenerEvaluacionPorParticipanteYPrueba(@RequestParam Integer participanteId, @RequestParam Integer pruebaId) {
        Optional<EvaluacionDTO> evaluacion = evaluacionService.obtenerPorParticipanteYPrueba(participanteId, pruebaId);
        return evaluacion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Agregar una nueva evaluación")
    public EvaluacionDTO agregar(@RequestBody EvaluacionDTO evaluacionDTO) {
        return evaluacionService.agregarEvaluacion(evaluacionDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una evaluación existente")
    public EvaluacionDTO actualizar(@PathVariable Integer id, @RequestBody EvaluacionDTO evaluacionDTO) {
        return evaluacionService.actualizarEvaluacion(id, evaluacionDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una evaluación")
    public void eliminar(@PathVariable Integer id) {
        evaluacionService.eliminarEvaluacion(id);
    }
}
