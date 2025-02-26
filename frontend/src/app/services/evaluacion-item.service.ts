import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface EvaluacionItemDTO {
  evaluacionId: number;
  itemId: number;
  valoracion: number;
}

export interface EvaluacionItemResponseDTO {
  idEvaluacionItem: number;
  evaluacionId: number;
  itemId: number;
  valoracion: number;
}

@Injectable({
  providedIn: 'root'
})
export class EvaluacionItemService {
  private apiUrl = 'http://localhost:8080/evaluacion-items';  // Ajusta la URL según tu API

  constructor(private http: HttpClient) { }

  agregarEvaluacionItem(evaluacionItem: EvaluacionItemDTO): Observable<EvaluacionItemDTO> {
    return this.http.post<EvaluacionItemDTO>(this.apiUrl, evaluacionItem);
  }

  obtenerEvaluacionItems(): Observable<EvaluacionItemDTO[]> {
    return this.http.get<EvaluacionItemDTO[]>(this.apiUrl);
  }

  actualizarEvaluacionItem(id: number, evaluacionItem: EvaluacionItemDTO): Observable<EvaluacionItemResponseDTO> {
    return this.http.put<EvaluacionItemResponseDTO>(`${this.apiUrl}/${id}`, evaluacionItem);
  }

  obtenerEvaluacionItemsPorEvaluacion(evaluacionId: number): Observable<EvaluacionItemResponseDTO[]> {
    return this.http.get<EvaluacionItemResponseDTO[]>(`${this.apiUrl}/evaluacion/${evaluacionId}`);
  }
}