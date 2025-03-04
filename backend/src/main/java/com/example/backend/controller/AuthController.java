package com.example.backend.controller;

import com.example.backend.dto.UserLoginDTO;
import com.example.backend.dto.UserRegisterDTO;
import com.example.backend.service.UserService;
import com.example.backend.config.JwtUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Autenticación", description = "Endpoints para autenticación de usuarios")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    @Operation(summary = "Registrar un nuevo usuario")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario registrado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error en el registro")
    })
    public ResponseEntity<Map<String, String>> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            userService.agregarUser(userRegisterDTO);
            response.put("message", "✅ Usuario registrado exitosamente.");
            return ResponseEntity.ok(response);
        } catch (DataIntegrityViolationException e) {
            response.put("error", "⚠️ El nombre de usuario ya está registrado.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión y obtener token JWT")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Autenticación exitosa"),
        @ApiResponse(responseCode = "401", description = "Credenciales incorrectas")
    })
    public ResponseEntity<Map<String, String>> login(@RequestBody UserLoginDTO userLoginDTO) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Usuario o contraseña incorrectos."));
        }

        final UserDetails userDetails = userService.loadUserByUsername(userLoginDTO.getUsername());
        final Integer userId = userService.getUserIdByUsername(userLoginDTO.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails, userService.getUserRole(userLoginDTO.getUsername()), userService.getUserEspecialidadId(userLoginDTO.getUsername()), userId);

        return ResponseEntity.ok(Map.of("token", jwt));
    }
}
