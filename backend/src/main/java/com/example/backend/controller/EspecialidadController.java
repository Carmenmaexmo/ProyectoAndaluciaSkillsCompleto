package com.example.backend.controller;

import com.example.backend.dto.EspecialidadDTO;
import com.example.backend.service.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/especialidades")
@CrossOrigin(origins = "http://localhost:4200")
public class EspecialidadController {

    @Autowired
    private EspecialidadService especialidadService;

    @GetMapping
    public List<EspecialidadDTO> getEspecialidades() {
        return especialidadService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Optional<EspecialidadDTO> obtenerEspecialidades(@PathVariable Integer id) {
        return especialidadService.obtenerPorId(id);
    }

    @PostMapping
    public EspecialidadDTO agregarEspecialidad(@RequestBody EspecialidadDTO especialidadDTO) {
        return especialidadService.agregarEspecialidad(especialidadDTO);
    }

    @PutMapping("/{id}")
    public EspecialidadDTO actualizarEspecialidad(@PathVariable Integer id, @RequestBody EspecialidadDTO especialidadDTO) {
        especialidadDTO.setIdEspecialidad(id);
        return especialidadService.agregarEspecialidad(especialidadDTO);
    }

    @DeleteMapping("/{id}")
    public void eliminarEspecialidad(@PathVariable Integer id) {
        especialidadService.eliminarEspecialidad(id);
    }
}