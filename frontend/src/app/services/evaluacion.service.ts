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

export interface EvaluacionItemDTO {
  itemId: number;
  valoracion: number;
}

export interface EvaluacionResponseDTO {
  idEvaluacion: number; 
  idParticipante: number;
  idUsuario: number;
  idPrueba: number;
  notaFinal: number;
  items: EvaluacionItemResponseDTO[];  // Añadir los ítems relacionados
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
}