package com.example.backend.service.base;

import com.example.backend.dto.PruebaDTO;
import java.util.List;
import java.util.Optional;

public interface PruebaServiceBase {
    List<PruebaDTO> obtenerTodas();
    Optional<PruebaDTO> obtenerPorId(Integer id);
    PruebaDTO agregarPrueba(PruebaDTO pruebaDTO);
    void eliminarPrueba(Integer id);
}