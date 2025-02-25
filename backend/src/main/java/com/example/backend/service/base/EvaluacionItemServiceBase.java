package com.example.backend.service.base;

import com.example.backend.dto.EvaluacionItemDTO;
import java.util.List;
import java.util.Optional;

public interface EvaluacionItemServiceBase {
    List<EvaluacionItemDTO> obtenerTodos();
    Optional<EvaluacionItemDTO> obtenerPorId(Integer id);
    EvaluacionItemDTO actualizarEvaluacionItem(Integer id, EvaluacionItemDTO evaluacionItemDTO);
    EvaluacionItemDTO agregarEvaluacionItem(EvaluacionItemDTO evaluacionItemDTO);
    void eliminarEvaluacionItem(Integer id);
}