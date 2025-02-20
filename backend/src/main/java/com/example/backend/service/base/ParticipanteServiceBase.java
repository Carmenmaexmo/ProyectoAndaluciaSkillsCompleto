package com.example.backend.service.base;

import com.example.backend.dto.ParticipanteDTO;
import java.util.List;
import java.util.Optional;

public interface ParticipanteServiceBase {
    List<ParticipanteDTO> obtenerTodos();
    Optional<ParticipanteDTO> obtenerPorId(Integer id);
    ParticipanteDTO agregarParticipante(ParticipanteDTO participanteDTO);
    void eliminarParticipante(Integer id);
}