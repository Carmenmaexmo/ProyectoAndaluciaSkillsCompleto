import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EvaluacionItemResponseDTO } from './evaluacion-item.service';

export interface EvaluacionDTO {
  participanteId: number;
  usuarioId: number;
  pruebaId: number;
  notaFinal: number;
}

export interface EvaluacionResponseDTO {
  idEvaluacion: number; 
  idParticipante: number;
  idUsuario: number;
  idPrueba: number;
  notaFinal: number;
  items: EvaluacionItemResponseDTO[];  // Añadir los ítems relacionados
}

export interface PruebaSimpleDTO {
  idPrueba: number;
  enunciado: string;
  puntuacionMaxima: number;
  especialidadId: number;
}

@Injectable({
  providedIn: 'root'
})
export class EvaluacionService {
  private apiUrl = 'http://localhost:8080/evaluaciones';  // Ajusta la URL según tu API

  constructor(private http: HttpClient) { }
  
  agregarEvaluacion(evaluacion: EvaluacionDTO): Observable<EvaluacionResponseDTO> {
    return this.http.post<EvaluacionResponseDTO>(this.apiUrl, evaluacion);
  }

  obtenerporid(id: number): Observable<EvaluacionResponseDTO> {
    return this.http.get<EvaluacionResponseDTO>(`${this.apiUrl}/${id}`);
  }

  actualizarEvaluacion(id: number, evaluacion: EvaluacionDTO): Observable<EvaluacionResponseDTO> {
    return this.http.put<EvaluacionResponseDTO>(`${this.apiUrl}/${id}`, evaluacion);
  }

  obtenerPruebasPorParticipante(participanteId: number): Observable<PruebaSimpleDTO[]> {
    return this.http.get<PruebaSimpleDTO[]>(`${this.apiUrl}/pruebas-por-participante?participanteId=${participanteId}`);
  }

  obtenerPorParticipanteYPrueba(participanteId: number, pruebaId: number): Observable<EvaluacionResponseDTO[]> {
    return this.http.get<EvaluacionResponseDTO[]>(`${this.apiUrl}/evaluacion-por-participante-y-prueba?participanteId=${participanteId}&pruebaId=${pruebaId}`);
  }
}