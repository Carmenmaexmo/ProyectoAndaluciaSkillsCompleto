package com.example.backend.controller;

import com.example.backend.dto.MejorNotaDTO;
import com.example.backend.dto.ParticipanteDTO;
import com.example.backend.dto.ParticipantePuntuacionProjection;
import com.example.backend.model.Participante;
import com.example.backend.service.ParticipanteService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/participantes")
public class ParticipanteController {

    private final ParticipanteService participanteService;

    public ParticipanteController(ParticipanteService participanteService) {
        this.participanteService = participanteService;
    }

    @GetMapping
    public List<ParticipanteDTO> listar() {
        return participanteService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Optional<ParticipanteDTO> obtener(@PathVariable Integer id) {
        return participanteService.obtenerPorId(id);
    }

    @PostMapping
    public ParticipanteDTO agregar(@RequestBody ParticipanteDTO participanteDTO) {
        return participanteService.agregarParticipante(participanteDTO);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        participanteService.eliminarParticipante(id);
    }

    @GetMapping("/mejor-nota")
    public ResponseEntity<List<MejorNotaDTO>> obtenerMejorNotaPorEspecialidad() {
        return ResponseEntity.ok(participanteService.obtenerMejorNotaPorEspecialidad());
    }

    @GetMapping("/especialidad/{especialidadId}/puntuaciones")
    public List<ParticipantePuntuacionProjection> obtenerPuntuacionesPorEspecialidad(@PathVariable Integer especialidadId) {
        return participanteService.obtenerPuntuacionesPorEspecialidad(especialidadId);
    }

    @GetMapping("/especialidad/{especialidadId}")
    public List<Participante> obtenerParticipantesPorEspecialidad(@PathVariable Integer especialidadId) {
        return participanteService.obtenerParticipantesPorEspecialidad(especialidadId);
    }
}