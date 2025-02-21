package com.example.backend.service.base;

import com.example.backend.dto.ParticipanteDTO;

import com.example.backend.dto.ParticipantePuntuacionProjection;

import java.util.List;
import java.util.Optional;

public interface ParticipanteServiceBase {
    List<ParticipanteDTO> obtenerTodos();
    Optional<ParticipanteDTO> obtenerPorId(Integer id);
    List<ParticipantePuntuacionProjection> obtenerPuntuacionesPorEspecialidad(Integer especialidadId);
    List<ParticipanteDTO> obtenerUsuariosPorRol(String rol);
    ParticipanteDTO agregarParticipante(ParticipanteDTO participanteDTO);
    void eliminarParticipante(Integer id);
}