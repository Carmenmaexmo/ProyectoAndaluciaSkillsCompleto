package com.example.backend.service;

import com.example.backend.dto.PruebaDTO;
import com.example.backend.dto.PruebaItemsEvaluacionProjection;
import com.example.backend.mapper.PruebaMapper;
import com.example.backend.model.Prueba;
import com.example.backend.model.Especialidad;
import com.example.backend.repository.PruebaRepository;
import com.example.backend.repository.EspecialidadRepository;
import com.example.backend.service.base.PruebaServiceBase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PruebaService implements PruebaServiceBase {

    private final PruebaRepository pruebaRepository;
    private final PruebaMapper pruebaMapper;
    private final EspecialidadRepository especialidadRepository;

    public PruebaService(PruebaRepository pruebaRepository, PruebaMapper pruebaMapper, EspecialidadRepository especialidadRepository) {
        this.pruebaRepository = pruebaRepository;
        this.pruebaMapper = pruebaMapper;
        this.especialidadRepository = especialidadRepository;
    }

    @Override
    public List<PruebaDTO> obtenerTodas() {
        return pruebaRepository.findAll().stream()
                .map(pruebaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PruebaDTO> obtenerPorId(Integer id) {
        return pruebaRepository.findById(id)
                .map(pruebaMapper::toDTO);
    }

    @Override
    public List<PruebaDTO> obtenerPruebasPorEspecialidad(Integer especialidadId) {
        return pruebaRepository.findByEspecialidad(especialidadId)
                .stream()
                .map(pruebaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PruebaItemsEvaluacionProjection> obtenerPruebasPorEspecialidadWithItems(Integer especialidadId) {
        return pruebaRepository.findByEspecialidadWithItems(especialidadId);
    }

    @Override
    public PruebaDTO agregarPrueba(PruebaDTO pruebaDTO) {
        Prueba prueba = pruebaMapper.toEntity(pruebaDTO);
        // Obtener la entidad relacionada (Especialidad) desde su repositorio
        Optional<Especialidad> especialidad = especialidadRepository.findById(pruebaDTO.getEspecialidadId());
        if (especialidad.isPresent()) {
            prueba.setEspecialidad(especialidad.get());
        } else {
            throw new IllegalArgumentException("Especialidad no encontrada");
        }
        Prueba pruebaGuardada = pruebaRepository.save(prueba);
        return pruebaMapper.toDTO(pruebaGuardada);
    }

    @Override
    public void eliminarPrueba(Integer id) {
        pruebaRepository.deleteById(id);
    }
}