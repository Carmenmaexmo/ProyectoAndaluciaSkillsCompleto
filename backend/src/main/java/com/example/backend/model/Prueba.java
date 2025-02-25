package com.example.backend.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "prueba")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Prueba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPrueba;

    @Column(nullable = true, length = 200)
    private String enunciado;

    @Column(nullable = true)
    private Integer puntuacion_maxima;

    @ManyToOne
    @JoinColumn(name = "Especialidad_id_Especialidad", nullable = false)
    @JsonIgnore
    private Especialidad especialidad;

    @OneToMany(mappedBy = "prueba")
    @JsonIgnore
    private List<Evaluacion> evaluaciones;

    // Getters y Setters
    public Integer getIdPrueba() { return idPrueba; }
    public void setIdPrueba(Integer idPrueba) { this.idPrueba = idPrueba; }

    public String getEnunciado() { return enunciado; }
    public void setEnunciado(String enunciado) { this.enunciado = enunciado; }

    public Integer getPuntuacionMaxima() { return puntuacion_maxima; }
    public void setPuntuacionMaxima(Integer puntuacion_maxima) { this.puntuacion_maxima = puntuacion_maxima; }

    public Especialidad getEspecialidad() { return especialidad; }
    public void setEspecialidad(Especialidad especialidad) { this.especialidad = especialidad; }

    public List<Evaluacion> getEvaluaciones() { return evaluaciones; }
    public void setEvaluaciones(List<Evaluacion> evaluaciones) { this.evaluaciones = evaluaciones; }
}