package com.example.backend.service;

import com.example.backend.dto.EvaluacionItemDTO;
import com.example.backend.model.Evaluacion;
import com.example.backend.model.EvaluacionItem;
import com.example.backend.model.Item;
import com.example.backend.repository.EvaluacionItemRepository;
import com.example.backend.repository.EvaluacionRepository;
import com.example.backend.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvaluacionItemService {

    private final EvaluacionItemRepository evaluacionItemRepository;
    private final EvaluacionRepository evaluacionRepository;
    private final ItemRepository itemRepository;

    public EvaluacionItemService(EvaluacionItemRepository evaluacionItemRepository, EvaluacionRepository evaluacionRepository, ItemRepository itemRepository) {
        this.evaluacionItemRepository = evaluacionItemRepository;
        this.evaluacionRepository = evaluacionRepository;
        this.itemRepository = itemRepository;
    }

    public List<EvaluacionItemDTO> obtenerTodos() {
        return evaluacionItemRepository.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    public Optional<EvaluacionItemDTO> obtenerPorId(Integer id) {
        return evaluacionItemRepository.findById(id).map(this::convertirADTO);
    }

    public EvaluacionItemDTO agregarEvaluacionItem(EvaluacionItemDTO evaluacionItemDTO) {
        EvaluacionItem evaluacionItem = convertirAEntidad(evaluacionItemDTO);
        EvaluacionItem nuevoEvaluacionItem = evaluacionItemRepository.save(evaluacionItem);
        return convertirADTO(nuevoEvaluacionItem);
    }

    public EvaluacionItemDTO actualizarEvaluacionItem(Integer id, EvaluacionItemDTO evaluacionItemDTO) {
        EvaluacionItem evaluacionItem = evaluacionItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evaluación de ítem no encontrada"));

        evaluacionItem.setValoracion(evaluacionItemDTO.getValoracion());

        EvaluacionItem evaluacionItemActualizada = evaluacionItemRepository.save(evaluacionItem);
        return convertirADTO(evaluacionItemActualizada);
    }

    public void eliminarEvaluacionItem(Integer id) {
        evaluacionItemRepository.deleteById(id);
    }

    private EvaluacionItemDTO convertirADTO(EvaluacionItem evaluacionItem) {
        EvaluacionItemDTO dto = new EvaluacionItemDTO();
        dto.setIdEvaluacionItem(evaluacionItem.getIdEvaluacionItem());
        dto.setValoracion(evaluacionItem.getValoracion());
        dto.setEvaluacionId(evaluacionItem.getEvaluacion().getIdEvaluacion());
        dto.setItemId(evaluacionItem.getItem().getIdItem());
        return dto;
    }

    private EvaluacionItem convertirAEntidad(EvaluacionItemDTO dto) {
        EvaluacionItem evaluacionItem = new EvaluacionItem();
        evaluacionItem.setIdEvaluacionItem(dto.getIdEvaluacionItem());
        evaluacionItem.setValoracion(dto.getValoracion());

        Evaluacion evaluacion = evaluacionRepository.findById(dto.getEvaluacionId())
                .orElseThrow(() -> new RuntimeException("Evaluacion no encontrada"));
        evaluacionItem.setEvaluacion(evaluacion);

        Item item = itemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));
        evaluacionItem.setItem(item);

        return evaluacionItem;
    }
}