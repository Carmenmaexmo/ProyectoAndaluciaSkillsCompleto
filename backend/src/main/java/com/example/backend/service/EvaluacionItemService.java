package com.example.backend.service;

import com.example.backend.dto.EvaluacionItemDTO;
import com.example.backend.mapper.EvaluacionItemMapper;
import com.example.backend.model.EvaluacionItem;
import com.example.backend.repository.EvaluacionItemRepository;
import com.example.backend.service.base.EvaluacionItemServiceBase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvaluacionItemService implements EvaluacionItemServiceBase {

    private final EvaluacionItemRepository evaluacionItemRepository;
    private final EvaluacionItemMapper evaluacionItemMapper;

    public EvaluacionItemService(EvaluacionItemRepository evaluacionItemRepository, EvaluacionItemMapper evaluacionItemMapper) {
        this.evaluacionItemRepository = evaluacionItemRepository;
        this.evaluacionItemMapper = evaluacionItemMapper;
    }

    @Override
    public List<EvaluacionItemDTO> obtenerTodos() {
        return evaluacionItemRepository.findAll().stream()
                .map(evaluacionItemMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<EvaluacionItemDTO> obtenerPorId(Integer id) {
        return evaluacionItemRepository.findById(id)
                .map(evaluacionItemMapper::toDTO);
    }

    @Override
    public EvaluacionItemDTO agregarEvaluacionItem(EvaluacionItemDTO evaluacionItemDTO) {
        EvaluacionItem evaluacionItem = evaluacionItemMapper.toEntity(evaluacionItemDTO);
        EvaluacionItem evaluacionItemGuardado = evaluacionItemRepository.save(evaluacionItem);
        return evaluacionItemMapper.toDTO(evaluacionItemGuardado);
    }

    @Override
    public void eliminarEvaluacionItem(Integer id) {
        evaluacionItemRepository.deleteById(id);
    }
}