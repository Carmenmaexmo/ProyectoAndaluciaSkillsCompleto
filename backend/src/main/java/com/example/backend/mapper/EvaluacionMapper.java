package com.example.backend.mapper;

import com.example.backend.dto.EvaluacionDTO;
import com.example.backend.model.Evaluacion;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EvaluacionMapper implements GenericMapper<Evaluacion, EvaluacionDTO> {

    @Override
    public EvaluacionDTO toDTO(Evaluacion entity) {
        if (entity == null) {
            return null;
        }
        EvaluacionDTO dto = new EvaluacionDTO();
        dto.setIdEvaluacion(entity.getIdEvaluacion());
        dto.setNotaFinal(entity.getNotaFinal());
        dto.setParticipanteId(entity.getParticipante().getIdParticipante());
        dto.setUsuarioId(entity.getUsuario().getIdUser());
        dto.setPruebaId(entity.getPrueba().getIdPrueba());
        return dto;
    }

    @Override
    public Evaluacion toEntity(EvaluacionDTO dto) {
        if (dto == null) {
            return null;
        }
        Evaluacion entity = new Evaluacion();
        entity.setIdEvaluacion(dto.getIdEvaluacion());
        entity.setNotaFinal(dto.getNotaFinal());
        // Aquí necesitarás obtener las entidades relacionadas (Participante, Usuario, Prueba) desde sus repositorios
        return entity;
    }

    @Override
    public List<EvaluacionDTO> toDTOs(List<Evaluacion> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Evaluacion> toEntities(List<EvaluacionDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
