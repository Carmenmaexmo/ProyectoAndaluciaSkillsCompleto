package com.example.backend.model;


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
    @JoinColumn(name = "evaluacion_id_evaluacion", nullable = false)
    private Evaluacion evaluacion;

    @ManyToOne
    @JoinColumn(name = "item_id_item", nullable = false)
    private Item item;

}
