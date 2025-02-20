package com.example.backend.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "item")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idItem;

    @Column(nullable = false, length = 200)
    private String descripcion;

    @Column(nullable = true)
    private Integer peso;

    @Column(nullable = true)
    private Integer grados_consecucion;

    @OneToMany(mappedBy = "item")
    private List<EvaluacionItem> evaluacionItems;
    @JsonIgnore

    // Getters y Setters
    public Integer getIdItem() { return idItem; }
    public void setIdItem(Integer idItem) { this.idItem = idItem; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getPeso() { return peso; }
    public void setPeso(Integer peso) { this.peso = peso; }

    public Integer getGradosConsecucion() { return grados_consecucion; }
    public void setGradosConsecucion(Integer grados_consecucion) { this.grados_consecucion = grados_consecucion; }

    public List<EvaluacionItem> getEvaluacionItems() { return evaluacionItems; }
    public void setEvaluacionItems(List<EvaluacionItem> evaluacionItems) { this.evaluacionItems = evaluacionItems; }
}