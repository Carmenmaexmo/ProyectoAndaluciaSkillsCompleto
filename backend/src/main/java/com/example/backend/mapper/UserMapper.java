package com.example.backend.mapper;

import com.example.backend.dto.UserDTO;
import com.example.backend.dto.UserRegisterDTO;
import com.example.backend.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper implements GenericMapper<User, UserDTO> {

    @Override
    public UserDTO toDTO(User entity) {
        if (entity == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        dto.setIdUser(entity.getIdUser());
        dto.setUsername(entity.getUsername());
        dto.setRole(entity.getRole());
        dto.setNombre(entity.getNombre());
        dto.setApellidos(entity.getApellidos());
        dto.setDni(entity.getDni());
        dto.setEspecialidadId(entity.getEspecialidad().getIdEspecialidad());
        return dto;
    }

    @Override
    public User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        User entity = new User();
        entity.setIdUser(dto.getIdUser());
        entity.setUsername(dto.getUsername());
        entity.setRole(dto.getRole());
        entity.setNombre(dto.getNombre());
        entity.setApellidos(dto.getApellidos());
        entity.setDni(dto.getDni());
        // Aquí necesitarás obtener la entidad relacionada (Especialidad) desde su repositorio
        return entity;
    }

    @Override
    public List<UserDTO> toDTOs(List<User> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> toEntities(List<UserDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public UserRegisterDTO toRegisterDTO(User entity) {
        if (entity == null) {
            return null;
        }
        UserRegisterDTO dto = new UserRegisterDTO();
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole());
        dto.setEspecialidadId(entity.getEspecialidad().getIdEspecialidad());
        dto.setDni(entity.getDni());
        dto.setApellidos(entity.getApellidos());
        dto.setNombre(entity.getNombre());
        return dto;
    }

    public User toEntity(UserRegisterDTO dto) {
        if (dto == null) {
            return null;
        }
        User entity = new User();
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setRole(dto.getRole());
        entity.setDni(dto.getDni());
        entity.setApellidos(dto.getApellidos());
        entity.setNombre(dto.getNombre());
        // Aquí necesitarás obtener la entidad relacionada (Especialidad) desde su repositorio
        return entity;
    }
}