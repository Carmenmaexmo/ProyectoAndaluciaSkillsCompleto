package com.example.backend.controller;

import com.example.backend.dto.PruebaDTO;
import com.example.backend.dto.PruebaItemsEvaluacionProjection;
import com.example.backend.service.PruebaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pruebas")
public class PruebaController {

    private final PruebaService pruebaService;

    public PruebaController(PruebaService pruebaService) {
        this.pruebaService = pruebaService;
    }

    @GetMapping
    public List<PruebaDTO> listar() {
        return pruebaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Optional<PruebaDTO> obtener(@PathVariable Integer id) {
        return pruebaService.obtenerPorId(id);
    }

    @GetMapping("/especialidad/{especialidadId}")
    public List<PruebaDTO> obtenerPruebasPorEspecialidad(@PathVariable Integer especialidadId) {
        return pruebaService.obtenerPruebasPorEspecialidad(especialidadId);
    }

     @GetMapping("/especialidad/{especialidadId}/items")
    public List<PruebaItemsEvaluacionProjection> obtenerPruebasPorEspecialidadWithItems(@PathVariable Integer especialidadId) {
        return pruebaService.obtenerPruebasPorEspecialidadWithItems(especialidadId);
    }

    @PostMapping
    public PruebaDTO agregar(@RequestBody PruebaDTO pruebaDTO) {
        return pruebaService.agregarPrueba(pruebaDTO);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        pruebaService.eliminarPrueba(id);
    }
}