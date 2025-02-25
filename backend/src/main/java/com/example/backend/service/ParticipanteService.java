package com.example.backend.service;

import com.example.backend.dto.MejorNotaDTO;
import com.example.backend.dto.ParticipanteDTO;
import com.example.backend.dto.ParticipantePuntuacionProjection;
import com.example.backend.mapper.ParticipanteMapper;
import com.example.backend.model.Especialidad;
import com.example.backend.model.Participante;
import com.example.backend.repository.EspecialidadRepository;
import com.example.backend.repository.ParticipanteRepository;
import com.example.backend.service.base.ParticipanteServiceBase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParticipanteService implements ParticipanteServiceBase {

    private final ParticipanteRepository participanteRepository;
    private final EspecialidadRepository especialidadRepository;
    private final ParticipanteMapper participanteMapper;

    public ParticipanteService(ParticipanteRepository participanteRepository, EspecialidadRepository especialidadRepository, ParticipanteMapper participanteMapper) {
        this.participanteRepository = participanteRepository;
        this.especialidadRepository = especialidadRepository;
        this.participanteMapper = participanteMapper;
    }

    public List<MejorNotaDTO> obtenerMejorNotaPorEspecialidad() {
        return participanteRepository.obtenerMejorNotaPorEspecialidad();
    }

    @Override
    public List<ParticipanteDTO> obtenerTodos() {
        return participanteRepository.findAll().stream()
                .map(participanteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ParticipanteDTO> obtenerPorId(Integer id) {
        return participanteRepository.findById(id)
                .map(participanteMapper::toDTO);
    }


    //obtener participantes por especialidad
    public List<Participante> obtenerParticipantesPorEspecialidad(Integer especialidadId) {
        return participanteRepository.findByEspecialidad(especialidadId);
    }

    @Override
    public ParticipanteDTO agregarParticipante(ParticipanteDTO participanteDTO) {
        Participante participante = participanteMapper.toEntity(participanteDTO);
        Especialidad especialidad = especialidadRepository.findById(participanteDTO.getEspecialidadId())
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        participante.setEspecialidad(especialidad);
        Participante participanteGuardado = participanteRepository.save(participante);
        return participanteMapper.toDTO(participanteGuardado);
    }

    @Override
    public void eliminarParticipante(Integer id) {
        participanteRepository.deleteById(id);
    }

    @Override
    public List<ParticipantePuntuacionProjection> obtenerPuntuacionesPorEspecialidad(Integer especialidadId) {
        return participanteRepository.obtenerPuntuacionesPorEspecialidad(especialidadId);
    }

    @Override
    public List<ParticipanteDTO> obtenerUsuariosPorRol(String rol) {
        return ((ParticipanteServiceBase) participanteRepository).obtenerUsuariosPorRol(rol);
    }

    public ParticipanteDTO actualizarParticipante(Integer id, ParticipanteDTO participanteDTO) {
        Participante participante = participanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Participante no encontrado"));
        participante.setNombre(participanteDTO.getNombre());
        participante.setApellidos(participanteDTO.getApellidos());
        participante.setCentro(participanteDTO.getCentro());
        Especialidad especialidad = especialidadRepository.findById(participanteDTO.getEspecialidadId())
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        participante.setEspecialidad(especialidad);
        Participante participanteActualizado = participanteRepository.save(participante);
        return participanteMapper.toDTO(participanteActualizado);
    }

}