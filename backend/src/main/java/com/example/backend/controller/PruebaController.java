package com.example.backend.controller;

import com.example.backend.dto.PruebaDTO;
import com.example.backend.dto.PruebaItemsEvaluacionProjection;
import com.example.backend.service.PruebaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pruebas")
@Tag(name = "Pruebas", description = "API para gestionar pruebas de evaluación")
public class PruebaController {

    private final PruebaService pruebaService;

    public PruebaController(PruebaService pruebaService) {
        this.pruebaService = pruebaService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las pruebas")
    public List<PruebaDTO> listar() {
        return pruebaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una prueba por ID")
    public Optional<PruebaDTO> obtener(@PathVariable Integer id) {
        return pruebaService.obtenerPorId(id);
    }

    @GetMapping("/especialidad/{especialidadId}")
    @Operation(summary = "Obtener pruebas por especialidad")
    public List<PruebaDTO> obtenerPruebasPorEspecialidad(@PathVariable Integer especialidadId) {
        return pruebaService.obtenerPruebasPorEspecialidad(especialidadId);
    }

    @GetMapping("/especialidad/{especialidadId}/items")
    @Operation(summary = "Obtener pruebas por especialidad con sus items")
    public List<PruebaItemsEvaluacionProjection> obtenerPruebasPorEspecialidadWithItems(@PathVariable Integer especialidadId) {
        return pruebaService.obtenerPruebasPorEspecialidadWithItems(especialidadId);
    }

    @PostMapping
    @Operation(summary = "Agregar una nueva prueba")
    public PruebaDTO agregar(@RequestBody PruebaDTO pruebaDTO) {
        return pruebaService.agregarPrueba(pruebaDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una prueba")
    public void eliminar(@PathVariable Integer id) {
        pruebaService.eliminarPrueba(id);
    }
}
