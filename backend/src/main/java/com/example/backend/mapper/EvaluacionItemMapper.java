package com.example.backend.mapper;

import com.example.backend.dto.EvaluacionItemDTO;
import com.example.backend.model.EvaluacionItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EvaluacionItemMapper implements GenericMapper<EvaluacionItem, EvaluacionItemDTO> {

    @Override
    public EvaluacionItemDTO toDTO(EvaluacionItem entity) {
        if (entity == null) {
            return null;
        }
        EvaluacionItemDTO dto = new EvaluacionItemDTO();
        dto.setIdEvaluacionItem(entity.getIdEvaluacionItem());
        dto.setValoracion(entity.getValoracion());
        dto.setEvaluacionId(entity.getEvaluacion().getIdEvaluacion());
        dto.setItemId(entity.getItem().getIdItem());
        return dto;
    }

    @Override
    public EvaluacionItem toEntity(EvaluacionItemDTO dto) {
        if (dto == null) {
            return null;
        }
        EvaluacionItem entity = new EvaluacionItem();
        entity.setIdEvaluacionItem(dto.getIdEvaluacionItem());
        entity.setValoracion(dto.getValoracion());
        // Aquí necesitarás obtener las entidades relacionadas (Evaluacion, Item) desde sus repositorios
        return entity;
    }

    @Override
    public List<EvaluacionItemDTO> toDTOs(List<EvaluacionItem> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EvaluacionItem> toEntities(List<EvaluacionItemDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
