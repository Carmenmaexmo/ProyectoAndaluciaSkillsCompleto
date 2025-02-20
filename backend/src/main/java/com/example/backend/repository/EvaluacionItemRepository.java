package com.example.backend.repository;

import com.example.backend.model.EvaluacionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface EvaluacionItemRepository extends JpaRepository<EvaluacionItem, Integer> {

    //Buscar Items de evaluacion por evaluacion
    @Query("SELECT ei FROM EvaluacionItem ei WHERE ei.evaluacion.idEvaluacion = :evaluacionId")
    List<EvaluacionItem> findByEvaluacion(Integer evaluacionId);
}