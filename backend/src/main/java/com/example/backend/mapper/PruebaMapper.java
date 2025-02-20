package com.example.backend.mapper;

import com.example.backend.dto.PruebaDTO;
import com.example.backend.model.Prueba;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PruebaMapper implements GenericMapper<Prueba, PruebaDTO> {

    @Override
    public PruebaDTO toDTO(Prueba entity) {
        if (entity == null) {
            return null;
        }
        PruebaDTO dto = new PruebaDTO();
        dto.setIdPrueba(entity.getIdPrueba());
        dto.setEnunciado(entity.getEnunciado());
        dto.setPuntuacionMaxima(entity.getPuntuacionMaxima());
        dto.setEspecialidadId(entity.getEspecialidad().getIdEspecialidad());
        return dto;
    }

    @Override
    public Prueba toEntity(PruebaDTO dto) {
        if (dto == null) {
            return null;
        }
        Prueba entity = new Prueba();
        entity.setIdPrueba(dto.getIdPrueba());
        entity.setEnunciado(dto.getEnunciado());
        entity.setPuntuacionMaxima(dto.getPuntuacionMaxima());
        // Aquí necesitarás obtener la entidad relacionada (Especialidad) desde su repositorio
        return entity;
    }

    @Override
    public List<PruebaDTO> toDTOs(List<Prueba> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Prueba> toEntities(List<PruebaDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}