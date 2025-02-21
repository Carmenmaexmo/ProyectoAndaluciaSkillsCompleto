package com.example.backend.service.base;

import com.example.backend.dto.UserDTO;
import com.example.backend.dto.UserRegisterDTO;

import java.util.List;
import java.util.Optional;

public interface UserServiceBase {
    List<UserDTO> obtenerTodos();
    Optional<UserDTO> obtenerPorId(Integer id);
    UserDTO agregarUser(UserRegisterDTO userRegisterDTO);
    void eliminarUser(Integer id);
    UserDTO actualizarUser(Integer id, UserRegisterDTO userRegisterDTO);
}