package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDTO {
    private Integer idUser;
    private String username;
    private String password;
    private String role;
    private String nombre;
    private String apellidos;
    private String dni;
    private Integer especialidadId;
}
