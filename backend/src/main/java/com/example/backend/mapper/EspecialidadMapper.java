package com.example.backend.mapper;

import com.example.backend.dto.EspecialidadDTO;
import com.example.backend.model.Especialidad;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EspecialidadMapper implements GenericMapper<Especialidad, EspecialidadDTO> {

    @Override
    public EspecialidadDTO toDTO(Especialidad entity) {
        if (entity == null) {
            return null;
        }
        EspecialidadDTO dto = new EspecialidadDTO();
        dto.setIdEspecialidad(entity.getIdEspecialidad());
        dto.setNombre(entity.getNombre());
        dto.setCodigo(entity.getCodigo());
        return dto;
    }

    @Override
    public Especialidad toEntity(EspecialidadDTO dto) {
        if (dto == null) {
            return null;
        }
        Especialidad entity = new Especialidad();
        entity.setIdEspecialidad(dto.getIdEspecialidad());
        entity.setNombre(dto.getNombre());
        entity.setCodigo(dto.getCodigo());
        return entity;
    }

    @Override
    public List<EspecialidadDTO> toDTOs(List<Especialidad> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Especialidad> toEntities(List<EspecialidadDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
