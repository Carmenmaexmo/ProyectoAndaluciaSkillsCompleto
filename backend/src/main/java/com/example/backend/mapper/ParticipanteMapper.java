package com.example.backend.mapper;

import com.example.backend.dto.ParticipanteDTO;
import com.example.backend.model.Participante;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class ParticipanteMapper implements GenericMapper<Participante, ParticipanteDTO> {

    @Override
    public ParticipanteDTO toDTO(Participante entity) {
        if (entity == null) {
            return null;
        }
        ParticipanteDTO dto = new ParticipanteDTO();
        dto.setIdParticipante(entity.getIdParticipante());
        dto.setNombre(entity.getNombre());
        dto.setApellidos(entity.getApellidos());
        dto.setCentro(entity.getCentro());
        dto.setEspecialidadId(entity.getEspecialidad().getIdEspecialidad());
        return dto;
    }

    @Override
    public Participante toEntity(ParticipanteDTO dto) {
        if (dto == null) {
            return null;
        }
        Participante entity = new Participante();
        entity.setIdParticipante(dto.getIdParticipante());
        entity.setNombre(dto.getNombre());
        entity.setApellidos(dto.getApellidos());
        entity.setCentro(dto.getCentro());
        // Aquí necesitarás obtener la entidad relacionada (Especialidad) desde su repositorio
        return entity;
    }

    @Override
    public List<ParticipanteDTO> toDTOs(List<Participante> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Participante> toEntities(List<ParticipanteDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
