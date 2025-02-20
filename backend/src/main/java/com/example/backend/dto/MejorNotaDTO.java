package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MejorNotaDTO {
    private Integer idParticipante;
    private String nombre;
    private String apellidos;
    private String centro;
    private String nombreEspecialidad;
    private String enunciadoPrueba;
    private Double notaMaxima;

    // Constructor
    public MejorNotaDTO(Integer idParticipante, String nombre, String apellidos, String centro,
                         String nombreEspecialidad, String enunciadoPrueba, Double notaMaxima) {
        this.idParticipante = idParticipante;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.centro = centro;
        this.nombreEspecialidad = nombreEspecialidad;
        this.enunciadoPrueba = enunciadoPrueba;
        this.notaMaxima = notaMaxima;
    }
}
