package com.example.backend.mapper;

import java.util.List;

/**
 * Interfaz genérica para mapear entre entidades y DTOs.
 *
 * @param <E> el tipo de la entidad
 * @param <D> el tipo del DTO
 */
public interface GenericMapper<E, D> {

    /**
     * Convierte una entidad en un DTO.
     *
     * @param entity la entidad a convertir
     * @return el DTO resultante
     */
    D toDTO(E entity);

    /**
     * Convierte un DTO en una entidad.
     *
     * @param dto el DTO a convertir
     * @return la entidad resultante
     */
    E toEntity(D dto);

    /**
     * Convierte una lista de entidades en una lista de DTOs.
     *
     * @param entities la lista de entidades a convertir
     * @return la lista de DTOs resultante
     */
    List<D> toDTOs(List<E> entities);

    /**
     * Convierte una lista de DTOs en una lista de entidades.
     *
     * @param dtos la lista de DTOs a convertir
     * @return la lista de entidades resultante
     */
    List<E> toEntities(List<D> dtos);
}