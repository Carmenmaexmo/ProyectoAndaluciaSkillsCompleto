package com.example.backend.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "user")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;

    @Column(nullable = false, length = 45)
    private String role;

    @Column(nullable = false, unique = true, length = 45)
    private String username;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String password;    

    @Column(nullable = true, length = 30)
    private String nombre;

    @Column(nullable = true, length = 60)
    private String apellidos;

    @Column(nullable = true, unique = true, length = 9)
    private String dni;

    @ManyToOne
    @JoinColumn(name = "Especialidad_idEspecialidad", nullable = false)
    @JsonIgnore
    private Especialidad especialidad;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<Evaluacion> evaluaciones;

    // Getters y Setters
    public Integer getIdUser() { return idUser; }
    public void setIdUser(Integer idUser) { this.idUser = idUser; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public Especialidad getEspecialidad() { return especialidad; }
    public void setEspecialidad(Especialidad especialidad) { this.especialidad = especialidad; }

    public List<Evaluacion> getEvaluaciones() { return evaluaciones; }
    public void setEvaluaciones(List<Evaluacion> evaluaciones) { this.evaluaciones = evaluaciones; }

}