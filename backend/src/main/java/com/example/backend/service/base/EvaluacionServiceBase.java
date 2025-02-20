package com.example.backend.service.base;

import com.example.backend.dto.EvaluacionDTO;
import java.util.List;
import java.util.Optional;

public interface EvaluacionServiceBase {
    List<EvaluacionDTO> obtenerTodas();
    Optional<EvaluacionDTO> obtenerPorId(Integer id);
    EvaluacionDTO agregarEvaluacion(EvaluacionDTO evaluacionDTO);
    void eliminarEvaluacion(Integer id);
}