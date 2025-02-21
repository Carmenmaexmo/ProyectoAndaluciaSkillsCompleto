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

    // Getters y Setters
    public Integer getIdEspecialidad() { return idEspecialidad; }
    public void setIdEspecialidad(Integer idEspecialidad) { this.idEspecialidad = idEspecialidad; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public List<Participante> getParticipantes() { return participantes; }
    public void setParticipantes(List<Participante> participantes) { this.participantes = participantes; }

    public List<Prueba> getPruebas() { return pruebas; }
    public void setPruebas(List<Prueba> pruebas) { this.pruebas = pruebas; }

    public List<User> getUsers() { return users; }
    public void setUsers(List<User> users) { this.users = users; }
   
}