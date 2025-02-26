package com.example.backend.controller;

import com.example.backend.dto.EvaluacionDTO;
import com.example.backend.dto.PruebaDTO;
import com.example.backend.service.EvaluacionService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/evaluaciones")
public class EvaluacionController {

    private final EvaluacionService evaluacionService;

    public EvaluacionController(EvaluacionService evaluacionService) {
        this.evaluacionService = evaluacionService;
    }

    @GetMapping
    public List<EvaluacionDTO> listar() {
        return evaluacionService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Optional<EvaluacionDTO> obtener(@PathVariable Integer id) {
        return evaluacionService.obtenerPorId(id);
    }

    @GetMapping("/pruebas-por-participante")
    public ResponseEntity<List<PruebaDTO>> obtenerPruebasPorParticipante(@RequestParam Integer participanteId) {
        List<PruebaDTO> pruebas = evaluacionService.obtenerPruebasPorParticipante(participanteId);
        return ResponseEntity.ok(pruebas);
    }

    @GetMapping("/evaluacion-por-participante-y-prueba")
    public ResponseEntity<EvaluacionDTO> obtenerEvaluacionPorParticipanteYPrueba(@RequestParam Integer participanteId, @RequestParam Integer pruebaId) {
        Optional<EvaluacionDTO> evaluacion = evaluacionService.obtenerPorParticipanteYPrueba(participanteId, pruebaId);
        return evaluacion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public EvaluacionDTO agregar(@RequestBody EvaluacionDTO evaluacionDTO) {
        return evaluacionService.agregarEvaluacion(evaluacionDTO);
    }

    @PutMapping("/{id}")
    public EvaluacionDTO actualizar(@PathVariable Integer id, @RequestBody EvaluacionDTO evaluacionDTO) {
        return evaluacionService.actualizarEvaluacion(id, evaluacionDTO);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        evaluacionService.eliminarEvaluacion(id);
    }
}