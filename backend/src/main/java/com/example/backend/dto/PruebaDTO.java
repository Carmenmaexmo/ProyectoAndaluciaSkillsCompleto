package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PruebaDTO {
    private Integer idPrueba;
    private String enunciado;
    private Integer puntuacionMaxima;
    private Integer especialidadId;
}
