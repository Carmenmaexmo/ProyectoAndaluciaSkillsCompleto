package com.example.backend.repository;

import com.example.backend.dto.MejorNotaDTO;
import com.example.backend.dto.ParticipantePuntuacionProjection;
import com.example.backend.model.Participante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Integer> {

    // Obtener el participante con la mejor nota por especialidad
    @Query(value = """
        SELECT 
            p.id_participante, 
            p.nombre AS Nombre_Participante, 
            p.apellidos AS Apellidos_Participante, 
            p.centro AS Centro_Participante, 
            e.Nombre AS Nombre_Especialidad, 
            pr.enunciado AS Enunciado_Prueba, 
            ev.nota_final AS Nota_Maxima
        FROM Evaluacion ev
        JOIN Participante p ON ev.participante_id_participante = p.id_participante
        JOIN Especialidad e ON p.Especialidad_id_Especialidad = e.id_Especialidad
        JOIN Prueba pr ON ev.Prueba_id_Prueba = pr.id_Prueba
        WHERE ev.nota_final = (
            SELECT MAX(ev2.nota_final)
            FROM Evaluacion ev2
            JOIN Participante p2 ON ev2.participante_id_participante = p2.id_participante
            JOIN Especialidad e2 ON p2.Especialidad_id_Especialidad = e2.id_Especialidad
            WHERE e2.id_Especialidad = e.id_Especialidad
            GROUP BY e2.id_Especialidad
        )
        ORDER BY e.Nombre;
        """, nativeQuery = true)
    List<MejorNotaDTO> obtenerMejorNotaPorEspecialidad();

    // Buscar participantes por especialidad (id)
    @Query("SELECT p FROM Participante p WHERE p.especialidad.idEspecialidad = :especialidadId")
    List<Participante> findByEspecialidad(Integer especialidadId);

    // Buscar participante por nombre
    List<Participante> findByNombreContaining(String nombre);

    // Obtener puntuaciones de cada prueba y la puntuación total de los participantes de una especialidad específica
    @Query(value = """
        SELECT 
            p.id_participante AS idParticipante,
            p.nombre AS nombre,
            p.apellidos AS apellidos,
            pr.enunciado AS enunciadoPrueba,
            ev.nota_final AS notaFinal,
            ROUND(SUM(ev.nota_final) OVER (PARTITION BY p.id_participante), 2) AS sumaNotas,
            (COUNT(pr.id_Prueba) OVER (PARTITION BY p.id_participante) * 10) AS sumaPuntuacionMaxima,
            CONCAT(ROUND(SUM(ev.nota_final) OVER (PARTITION BY p.id_participante), 2), '/', (COUNT(pr.id_Prueba) OVER (PARTITION BY p.id_participante) * 10)) AS puntuacionTotal
        FROM Participante p
        JOIN Evaluacion ev ON p.id_participante = ev.participante_id_participante
        JOIN Prueba pr ON ev.Prueba_id_Prueba = pr.id_Prueba
        WHERE p.Especialidad_id_Especialidad = :especialidadId
        ORDER BY p.id_participante, pr.id_Prueba;
        """, nativeQuery = true)
    List<ParticipantePuntuacionProjection> obtenerPuntuacionesPorEspecialidad(Integer especialidadId);


}