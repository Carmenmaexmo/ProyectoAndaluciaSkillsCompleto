package com.example.backend.service;

import com.example.backend.dto.EvaluacionDTO;
import com.example.backend.mapper.EvaluacionMapper;
import com.example.backend.model.Evaluacion;
import com.example.backend.repository.EvaluacionRepository;
import com.example.backend.service.base.EvaluacionServiceBase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvaluacionService implements EvaluacionServiceBase {

    private final EvaluacionRepository evaluacionRepository;
    private final EvaluacionMapper evaluacionMapper;

    public EvaluacionService(EvaluacionRepository evaluacionRepository, EvaluacionMapper evaluacionMapper) {
        this.evaluacionRepository = evaluacionRepository;
        this.evaluacionMapper = evaluacionMapper;
    }

    @Override
    public List<EvaluacionDTO> obtenerTodas() {
        return evaluacionRepository.findAll().stream()
                .map(evaluacionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<EvaluacionDTO> obtenerPorId(Integer id) {
        return evaluacionRepository.findById(id)
                .map(evaluacionMapper::toDTO);
    }

    @Override
    public EvaluacionDTO agregarEvaluacion(EvaluacionDTO evaluacionDTO) {
        Evaluacion evaluacion = evaluacionMapper.toEntity(evaluacionDTO);
        Evaluacion evaluacionGuardada = evaluacionRepository.save(evaluacion);
        return evaluacionMapper.toDTO(evaluacionGuardada);
    }

    @Override
    public void eliminarEvaluacion(Integer id) {
        evaluacionRepository.deleteById(id);
    }
}