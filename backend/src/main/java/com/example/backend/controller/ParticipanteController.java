package com.example.backend.controller;

import com.example.backend.dto.MejorNotaDTO;
import com.example.backend.dto.ParticipanteDTO;
import com.example.backend.dto.ParticipantePuntuacionProjection;
import com.example.backend.model.Participante;
import com.example.backend.service.ParticipanteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/participantes")
@Tag(name = "Participantes", description = "API para gestionar participantes")
public class ParticipanteController {

    private final ParticipanteService participanteService;

    public ParticipanteController(ParticipanteService participanteService) {
        this.participanteService = participanteService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los participantes")
    public List<ParticipanteDTO> listar() {
        return participanteService.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un participante por ID")
    public Optional<ParticipanteDTO> obtener(@PathVariable Integer id) {
        return participanteService.obtenerPorId(id);
    }

    @PostMapping
    @Operation(summary = "Agregar un nuevo participante")
    public ParticipanteDTO agregar(@RequestBody ParticipanteDTO participanteDTO) {
        return participanteService.agregarParticipante(participanteDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un participante")
    public ResponseEntity<ParticipanteDTO> actualizar(@PathVariable Integer id, @RequestBody ParticipanteDTO participanteDTO) {
        ParticipanteDTO actualizado = participanteService.actualizarParticipante(id, participanteDTO);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un participante")
    public void eliminar(@PathVariable Integer id) {
        participanteService.eliminarParticipante(id);
    }

    @GetMapping("/mejor-nota")
    @Operation(summary = "Obtener la mejor nota por especialidad")
    public ResponseEntity<List<MejorNotaDTO>> obtenerMejorNotaPorEspecialidad() {
        return ResponseEntity.ok(participanteService.obtenerMejorNotaPorEspecialidad());
    }

    @GetMapping("/especialidad/{especialidadId}/puntuaciones")
    @Operation(summary = "Obtener puntuaciones por especialidad")
    public List<ParticipantePuntuacionProjection> obtenerPuntuacionesPorEspecialidad(@PathVariable Integer especialidadId) {
        return participanteService.obtenerPuntuacionesPorEspecialidad(especialidadId);
    }

    @GetMapping("/especialidad/{especialidadId}")
    @Operation(summary = "Obtener participantes por especialidad")
    public List<Participante> obtenerParticipantesPorEspecialidad(@PathVariable Integer especialidadId) {
        return participanteService.obtenerParticipantesPorEspecialidad(especialidadId);
    }
}
