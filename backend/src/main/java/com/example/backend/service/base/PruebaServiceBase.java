package com.example.backend.service.base;

import com.example.backend.dto.PruebaDTO;
import com.example.backend.dto.PruebaItemsEvaluacionProjection;

import java.util.List;
import java.util.Optional;

public interface PruebaServiceBase {
    List<PruebaDTO> obtenerTodas();
    Optional<PruebaDTO> obtenerPorId(Integer id);
    List<PruebaDTO> obtenerPruebasPorEspecialidad(Integer especialidadId);
    List<PruebaItemsEvaluacionProjection> obtenerPruebasPorEspecialidadWithItems(Integer especialidadId);
    PruebaDTO agregarPrueba(PruebaDTO pruebaDTO);
    void eliminarPrueba(Integer id);
}