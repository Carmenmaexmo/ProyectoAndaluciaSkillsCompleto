package com.example.backend.service;

import com.example.backend.dto.EvaluacionDTO;
import com.example.backend.dto.PruebaDTO;
import com.example.backend.model.Evaluacion;
import com.example.backend.model.Participante;
import com.example.backend.model.Prueba;
import com.example.backend.model.User;
import com.example.backend.repository.EvaluacionRepository;
import com.example.backend.repository.ParticipanteRepository;
import com.example.backend.repository.PruebaRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvaluacionService {

    private final EvaluacionRepository evaluacionRepository;
    private final ParticipanteRepository participanteRepository;
    private final UserRepository userRepository;
    private final PruebaRepository pruebaRepository;

    public EvaluacionService(EvaluacionRepository evaluacionRepository, ParticipanteRepository participanteRepository, UserRepository userRepository, PruebaRepository pruebaRepository) {
        this.evaluacionRepository = evaluacionRepository;
        this.participanteRepository = participanteRepository;
        this.userRepository = userRepository;
        this.pruebaRepository = pruebaRepository;
    }

    public List<EvaluacionDTO> obtenerTodas() {
        return evaluacionRepository.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    public List<PruebaDTO> obtenerPruebasPorParticipante(Integer participanteId) {
        List<Evaluacion> evaluaciones = evaluacionRepository.findByParticipante(participanteId);
        return evaluaciones.stream().map(evaluacion -> {
            Prueba prueba = evaluacion.getPrueba();
            PruebaDTO pruebaDTO = new PruebaDTO();
            pruebaDTO.setIdPrueba(prueba.getIdPrueba());
            pruebaDTO.setEnunciado(prueba.getEnunciado());
            pruebaDTO.setPuntuacionMaxima(prueba.getPuntuacionMaxima());
            pruebaDTO.setEspecialidadId(prueba.getEspecialidad().getIdEspecialidad());
            return pruebaDTO;
        }).collect(Collectors.toList());
    }

    public Optional<EvaluacionDTO> obtenerPorId(Integer id) {
        return evaluacionRepository.findById(id).map(this::convertirADTO);
    }

    public EvaluacionDTO agregarEvaluacion(EvaluacionDTO evaluacionDTO) {
        Evaluacion evaluacion = convertirAEntidad(evaluacionDTO);
        Evaluacion nuevaEvaluacion = evaluacionRepository.save(evaluacion);
        return convertirADTO(nuevaEvaluacion);
    }

    public EvaluacionDTO actualizarEvaluacion(Integer id, EvaluacionDTO evaluacionDTO) {
        Evaluacion evaluacion = evaluacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evaluación no encontrada"));

        evaluacion.setNotaFinal(evaluacionDTO.getNotaFinal());

        Evaluacion evaluacionActualizada = evaluacionRepository.save(evaluacion);
        return convertirADTO(evaluacionActualizada);
    }

    public void eliminarEvaluacion(Integer id) {
        evaluacionRepository.deleteById(id);
    }

    private EvaluacionDTO convertirADTO(Evaluacion evaluacion) {
        EvaluacionDTO dto = new EvaluacionDTO();
        dto.setIdEvaluacion(evaluacion.getIdEvaluacion());
        dto.setNotaFinal(evaluacion.getNotaFinal());
        dto.setParticipanteId(evaluacion.getParticipante().getIdParticipante());
        dto.setUsuarioId(evaluacion.getUsuario().getIdUser());
        dto.setPruebaId(evaluacion.getPrueba().getIdPrueba());
        return dto;
    }

    private Evaluacion convertirAEntidad(EvaluacionDTO dto) {
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setIdEvaluacion(dto.getIdEvaluacion());
        evaluacion.setNotaFinal(dto.getNotaFinal());

        Participante participante = participanteRepository.findById(dto.getParticipanteId())
                .orElseThrow(() -> new RuntimeException("Participante no encontrado"));
        evaluacion.setParticipante(participante);

        User usuario = userRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        evaluacion.setUsuario(usuario);

        Prueba prueba = pruebaRepository.findById(dto.getPruebaId())
                .orElseThrow(() -> new RuntimeException("Prueba no encontrada"));
        evaluacion.setPrueba(prueba);

        return evaluacion;
    }

    public Optional<EvaluacionDTO> obtenerPorParticipanteYPrueba(Integer participanteId, Integer pruebaId) {
        List<Evaluacion> evaluaciones = evaluacionRepository.findByParticipanteAndPrueba(participanteId, pruebaId);
        if (evaluaciones.isEmpty()) {
            return Optional.empty();
        }
        return evaluaciones.stream().findFirst().map(this::convertirADTO);
    }

}