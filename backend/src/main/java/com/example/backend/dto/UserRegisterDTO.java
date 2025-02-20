package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserRegisterDTO {
    private String username;
    private String password;
    private String nombre;
    private String apellidos;
    private String dni;
    private String role;
    private Integer especialidadId;
}
