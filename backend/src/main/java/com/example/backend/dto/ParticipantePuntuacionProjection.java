package com.example.backend.dto;

public interface ParticipantePuntuacionProjection {
    Integer getIdParticipante();
    String getNombre();
    String getApellidos();
    String getEnunciadoPrueba();
    Double getNotaFinal();
    Double getSumaNotas();
    Double getSumaPuntuacionMaxima();
    String getPuntuacionTotal();
}