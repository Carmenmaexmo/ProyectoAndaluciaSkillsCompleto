package com.example.backend.controller;

import com.example.backend.dto.UserLoginDTO;
import com.example.backend.dto.UserRegisterDTO;
import com.example.backend.service.UserService;
import com.example.backend.config.JwtUtil;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")  // Permite que la aplicación front-end acceda a este endpoint
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            userService.agregarUser(userRegisterDTO);
            response.put("message", "✅ Usuario registrado exitosamente.");
            return ResponseEntity.ok(response);
        } catch (DataIntegrityViolationException e) {
            response.put("error", "⚠️ El nombre de usuario ya está registrado.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("error", "⚠️ Error en el registro.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserLoginDTO userLoginDTO) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Usuario o contraseña incorrectos."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error en el login."));
        }

        final UserDetails userDetails = userService.loadUserByUsername(userLoginDTO.getUsername());
        final String role = userService.getUserRole(userLoginDTO.getUsername());
        final Integer especialidadId = userService.getUserEspecialidadId(userLoginDTO.getUsername());
        final Integer userId = userService.getUserIdByUsername(userLoginDTO.getUsername());
        
        // Log para verificar que el userId se está obteniendo correctamente
        System.out.println("Obtenido userId: " + userId);
        
        final String jwt = jwtUtil.generateToken(userDetails, role, especialidadId, userId);

        Map<String, String> response = new HashMap<>();
        response.put("token", jwt);
        return ResponseEntity.ok(response);
    }
}