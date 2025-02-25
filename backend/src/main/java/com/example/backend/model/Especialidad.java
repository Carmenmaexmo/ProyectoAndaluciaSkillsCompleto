package com.example.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "especialidad")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Especialidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEspecialidad;

    @Column(nullable = false, length = 45)
    private String nombre;

    @Column(nullable = true, length = 4)
    private String codigo;

    @OneToMany(mappedBy = "especialidad")
    @JsonIgnore
    private List<Participante> participantes;

    @OneToMany(mappedBy = "especialidad")
    @JsonIgnore
    private List<Prueba> pruebas;

    @OneToMany(mappedBy = "especialidad")
    @JsonIgnore
    private List<User> users;
   
}