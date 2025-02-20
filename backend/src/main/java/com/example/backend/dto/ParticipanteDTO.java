package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ParticipanteDTO {
    private Integer idParticipante;
    private String nombre;
    private String apellidos;
    private String centro;
    private Integer especialidadId;
}

