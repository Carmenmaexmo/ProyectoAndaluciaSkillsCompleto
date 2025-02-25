package com.example.backend.dto;

public interface PruebaItemsEvaluacionProjection {
    Integer getEspecialidadIdEspecialidad();
    Integer getIdPrueba();
    String getEnunciado();
    Integer getPuntuacionMaxima();
    Integer getIdItem();
    String getDescripcion();
    Integer getPeso();
    Integer getGradosConsecucion();
    Integer getIdEvaluacionItem();
    Integer getValoracion();
    Integer getIdEvaluacion();
    Integer getPruebaIdPrueba();
    Integer getNotaFinal();
    Integer getParticipanteIdParticipante();
    Integer getIdParticipante();
    String getNombreParticipante();
    String getApellidosParticipante();
    String getCentroParticipante();
}