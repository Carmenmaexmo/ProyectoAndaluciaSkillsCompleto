package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EvaluacionDTO {
    private Integer idEvaluacion;
    private Double notaFinal;
    private Integer participanteId;
    private Integer usuarioId;
    private Integer pruebaId;
}