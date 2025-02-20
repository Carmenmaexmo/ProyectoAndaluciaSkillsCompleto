package com.example.backend.mapper;

import com.example.backend.dto.ItemDTO;
import com.example.backend.model.Item;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemMapper implements GenericMapper<Item, ItemDTO> {

    @Override
    public ItemDTO toDTO(Item entity) {
        if (entity == null) {
            return null;
        }
        ItemDTO dto = new ItemDTO();
        dto.setIdItem(entity.getIdItem());
        dto.setDescripcion(entity.getDescripcion());
        dto.setPeso(entity.getPeso());
        dto.setGradosConsecucion(entity.getGradosConsecucion());
        return dto;
    }

    @Override
    public Item toEntity(ItemDTO dto) {
        if (dto == null) {
            return null;
        }
        Item entity = new Item();
        entity.setIdItem(dto.getIdItem());
        entity.setDescripcion(dto.getDescripcion());
        entity.setPeso(dto.getPeso());
        entity.setGradosConsecucion(dto.getGradosConsecucion());
        return entity;
    }

    @Override
    public List<ItemDTO> toDTOs(List<Item> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> toEntities(List<ItemDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
