package com.example.backend.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "evaluacion_item")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EvaluacionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEvaluacionItem;

    @Column(nullable = true)
    private Integer valoracion;

    @ManyToOne
    @JoinColumn(name = "Evaluacion_idEvaluacion", nullable = false)
     @JsonIgnore
    private Evaluacion evaluacion;

    @ManyToOne
    @JoinColumn(name = "Item_idItem", nullable = false)
    @JsonIgnore
    private Item item;

    // Getters y Setters
    public Integer getIdEvaluacionItem() { return idEvaluacionItem; }
    public void setIdEvaluacionItem(Integer idEvaluacionItem) { this.idEvaluacionItem = idEvaluacionItem; }

    public Integer getValoracion() { return valoracion; }
    public void setValoracion(Integer valoracion) { this.valoracion = valoracion; }

    public Evaluacion getEvaluacion() { return evaluacion; }
    public void setEvaluacion(Evaluacion evaluacion) { this.evaluacion = evaluacion; }

    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item; }
}
