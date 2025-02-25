package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ParticipantePuntuacionDTO {
    private Integer idParticipante;
    private String nombre;
    private String apellidos;
    private String enunciadoPrueba;
    private Double notaFinal;
    private Double sumaNotas;
    private Double sumaPuntuacionMaxima;
    private String puntuacionTotal;

    // Constructor
    public ParticipantePuntuacionDTO(Integer idParticipante, String nombre, String apellidos, String enunciadoPrueba, Double notaFinal, Double sumaNotas, Double sumaPuntuacionMaxima, String puntuacionTotal) {
        this.idParticipante = idParticipante;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.enunciadoPrueba = enunciadoPrueba;
        this.notaFinal = notaFinal;
        this.sumaNotas = sumaNotas;
        this.sumaPuntuacionMaxima = sumaPuntuacionMaxima;
        this.puntuacionTotal = puntuacionTotal;
    }
}