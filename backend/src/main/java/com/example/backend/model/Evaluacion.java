package com.example.backend.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "evaluacion")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Evaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEvaluacion;

    @Column(nullable = true)
    private Double nota_final;

    // Relación con Participante
    @ManyToOne
    @JoinColumn(name = "Participante_idParticipante", nullable = false)
    @JsonIgnore
    private Participante participante;

    // Relación con Usuario (Evaluador)
    @ManyToOne
    @JoinColumn(name = "User_idUser", nullable = false)
    @JsonIgnore
    private User usuario;

    // Relación con Prueba
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "Prueba_idPrueba", nullable = false)
    
    private Prueba prueba;

    // Relación con EvaluacionItem
    @OneToMany(mappedBy = "evaluacion")
    @JsonIgnore
    private List<EvaluacionItem> evaluacionItems;

    // Getters y Setters
    public Integer getIdEvaluacion() { return idEvaluacion; }
    public void setIdEvaluacion(Integer idEvaluacion) { this.idEvaluacion = idEvaluacion; }

    public Double getNotaFinal() { return nota_final; }
    public void setNotaFinal(Double nota_final) { this.nota_final = nota_final; }

    public Participante getParticipante() { return participante; }
    public void setParticipante(Participante participante) { this.participante = participante; }

    public User getEvaluador() { return usuario; }
    public void setEvaluador(User usuario) { this.usuario = usuario; }

    public Prueba getPrueba() { return prueba; }
    public void setPrueba(Prueba prueba) { this.prueba = prueba; }

    public List<EvaluacionItem> getEvaluacionItems() { return evaluacionItems; }
    public void setEvaluacionItems(List<EvaluacionItem> evaluacionItems) { this.evaluacionItems = evaluacionItems; }
}
