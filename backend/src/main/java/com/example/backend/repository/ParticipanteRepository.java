package com.example.backend.repository;

import com.example.backend.dto.MejorNotaDTO;
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

    //Buscar participantes por especialidad (id)
    @Query("SELECT p FROM Participante p WHERE p.especialidad.idEspecialidad = :especialidadId")
    List<Participante> findByEspecialidad(Integer especialidadId);

    //Buscar participante por nombre
    List<Participante> findByNombreContaining(String nombre);
}