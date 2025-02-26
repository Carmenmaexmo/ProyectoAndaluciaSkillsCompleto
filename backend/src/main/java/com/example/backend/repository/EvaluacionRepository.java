package com.example.backend.repository;

import com.example.backend.model.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Integer> {

    //Buscar evaluaciones de un participante
    @Query("SELECT e FROM Evaluacion e WHERE e.participante.idParticipante = :participanteId")
    List<Evaluacion> findByParticipante(Integer participanteId);

    //Buscar evaluaciones de un usuario (experto)
    @Query("SELECT e FROM Evaluacion e WHERE e.usuario.idUser = :userId")
    List<Evaluacion> findByUsuario(Integer userId);

    //Buscar evaluacion por participante y prueba
    @Query("SELECT e FROM Evaluacion e WHERE e.participante.idParticipante = :participanteId AND e.prueba.idPrueba = :pruebaId")
    List<Evaluacion> findByParticipanteAndPrueba(Integer participanteId, Integer pruebaId);

}