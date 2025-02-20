package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EspecialidadDTO {
    @JsonProperty("id_especialidad")
    private Integer idEspecialidad;
    private String nombre;
    private String codigo;
}
