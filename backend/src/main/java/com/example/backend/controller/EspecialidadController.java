package com.example.backend.controller;

import com.example.backend.dto.EspecialidadDTO;
import com.example.backend.service.EspecialidadService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/especialidades")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Especialidades", description = "API para gestionar especialidades")
public class EspecialidadController {

    @Autowired
    private EspecialidadService especialidadService;

    @GetMapping
    @Operation(summary = "Obtener todas las especialidades")
    public List<EspecialidadDTO> getEspecialidades() {
        return especialidadService.obtenerTodas();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una especialidad por ID")
    public Optional<EspecialidadDTO> obtenerEspecialidades(@PathVariable Integer id) {
        return especialidadService.obtenerPorId(id);
    }

    @PostMapping
    @Operation(summary = "Agregar una nueva especialidad")
    public EspecialidadDTO agregarEspecialidad(@RequestBody EspecialidadDTO especialidadDTO) {
        return especialidadService.agregarEspecialidad(especialidadDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una especialidad existente")
    public EspecialidadDTO actualizarEspecialidad(@PathVariable Integer id, @RequestBody EspecialidadDTO especialidadDTO) {
        especialidadDTO.setIdEspecialidad(id);
        return especialidadService.agregarEspecialidad(especialidadDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una especialidad")
    public void eliminarEspecialidad(@PathVariable Integer id) {
        especialidadService.eliminarEspecialidad(id);
    }
}
