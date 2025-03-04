package com.example.backend.controller;

import com.example.backend.dto.UserDTO;
import com.example.backend.dto.UserRegisterDTO;
import com.example.backend.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuarios", description = "API para gestionar usuarios")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los usuarios")
    public List<UserDTO> listar() {
        return userService.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un usuario por ID")
    public Optional<UserDTO> obtener(@PathVariable Integer id) {
        return userService.obtenerPorId(id);
    }

    @GetMapping("/role/{rol}")
    @Operation(summary = "Obtener usuarios por rol")
    public List<UserDTO> obtenerUsuariosPorRol(@PathVariable String rol) {
        return userService.obtenerUsuariosPorRol(rol);
    }

    @GetMapping("/username/{username}")
    @Operation(summary = "Obtener ID de usuario por nombre de usuario")
    public Integer getUserIdByUsername(@PathVariable String username) {
        return userService.getUserIdByUsername(username);
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo usuario")
    public UserDTO agregar(@RequestBody UserRegisterDTO userRegisterDTO) {
        return userService.agregarUser(userRegisterDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar información de un usuario")
    public UserDTO actualizarUsuario(@PathVariable Integer id, @RequestBody UserRegisterDTO userRegisterDTO) {
        return userService.actualizarUser(id, userRegisterDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un usuario")
    public void eliminar(@PathVariable Integer id) {
        userService.eliminarUser(id);
    }
}
