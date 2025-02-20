package com.example.backend.controller; // filepath: /c:/Users/Carmen/Documents/DAW/2º/Servidor/servidor/ProyectoAndaluciaSkills/Backend/andaluciaskills/src/main/java/com/example/andaluciaskills/controller/EvaluacionController.java

import com.example.backend.dto.EvaluacionDTO;
import com.example.backend.service.EvaluacionService;
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

    @PostMapping
    public EvaluacionDTO agregar(@RequestBody EvaluacionDTO evaluacionDTO) {
        return evaluacionService.agregarEvaluacion(evaluacionDTO);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        evaluacionService.eliminarEvaluacion(id);
    }
}