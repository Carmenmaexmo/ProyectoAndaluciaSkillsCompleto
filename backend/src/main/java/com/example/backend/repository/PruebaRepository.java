package com.example.backend.repository;

import com.example.backend.dto.PruebaItemsEvaluacionProjection;
import com.example.backend.model.Prueba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface PruebaRepository extends JpaRepository<Prueba, Integer> {

    // Buscar pruebas por especialidad
    @Query("SELECT p FROM Prueba p WHERE p.especialidad.idEspecialidad = :especialidadId")
    List<Prueba> findByEspecialidad(Integer especialidadId);

    // Buscar pruebas por especialidad con sus items y su evaluacion_items
    @Query(value = """
    SELECT 
        p.especialidad_id_especialidad AS especialidadIdEspecialidad,
        p.id_Prueba AS idPrueba,
        p.enunciado AS enunciado,
        p.puntuacion_maxima AS puntuacionMaxima,
        i.id_Item AS idItem,
        i.descripcion AS descripcion,
        i.peso AS peso,
        i.grados_consecucion AS gradosConsecucion,
        ei.id_Evaluacion_Item AS idEvaluacionItem,
        ei.valoracion AS valoracion,
        e.id_Evaluacion AS idEvaluacion,
        e.prueba_id_prueba AS pruebaIdPrueba,
        e.nota_final AS notaFinal,
        par.id_Participante AS idParticipante,
        par.nombre AS nombreParticipante,
        par.apellidos AS apellidosParticipante,
        par.centro AS centroParticipante
        FROM Prueba p
        JOIN Evaluacion e ON e.Prueba_id_Prueba = p.id_Prueba  -- Relación entre Evaluación y Prueba
        JOIN Evaluacion_Item ei ON ei.Evaluacion_id_Evaluacion = e.id_Evaluacion  -- Relación entre Evaluación e Ítems evaluados
        JOIN Item i ON i.id_Item = ei.Item_id_Item  -- Relación entre Ítems y Evaluación_Item
        JOIN Participante par ON par.id_Participante = e.Participante_id_Participante  -- Relación entre Evaluación y Participante
        WHERE p.Especialidad_id_Especialidad = :especialidadId;
    """, nativeQuery = true)
    List<PruebaItemsEvaluacionProjection> findByEspecialidadWithItems(Integer especialidadId);
}