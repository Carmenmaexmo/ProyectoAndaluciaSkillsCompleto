import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';


export interface ItemDTO {
  idItem: number;
  descripcion: string;
  peso: number;
  gradosConsecucion: number;
}

export interface PruebaDTO {
  idPrueba: number;
  enunciado: string;
  puntuacionMaxima: number;
  items: ItemDTO[];
}

export interface CrearPruebaDTO {
  enunciado: string;
  puntuacion_maxima: number;
  especialidadId: number;
}

export interface PruebaItemsEvaluacionDTO {
  valoracion: number;
  notaFinal: number;
  idEvaluacion: number;
  idItem: number;
  idPrueba: number;
  peso: number;
  descripcion: string;
  enunciado: string;
  gradosConsecucion: number;
  puntuacionMaxima: number;
  idEvaluacionItem: number;
  pruebaIdPrueba: number;
  especialidadIdEspecialidad: number;
  nombreParticipante: string;
  apellidosParticipante: string;
}

@Injectable({
  providedIn: 'root'
})
export class PruebaService {
  private apiUrl = 'http://localhost:8080/pruebas';  // Ajusta la URL según tu API

  constructor(private http: HttpClient) { }

  obtenerPruebasPorEspecialidad(especialidadId: number): Observable<PruebaDTO[]> {
    return this.http.get<PruebaDTO[]>(`${this.apiUrl}/especialidad/${especialidadId}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  obtenerPruebasPorEspecialidadWithItems(especialidadId: number): Observable<PruebaItemsEvaluacionDTO[]> {
    return this.http.get<PruebaItemsEvaluacionDTO[]>(`${this.apiUrl}/especialidad/${especialidadId}/items`)
      .pipe(
        catchError(this.handleError)
      );
  }

  agregarPrueba(prueba: CrearPruebaDTO): Observable<CrearPruebaDTO> {
    return this.http.post<CrearPruebaDTO>(this.apiUrl, prueba);
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // Error del lado del cliente o de la red
      console.error('Ocurrió un error:', error.error.message);
    } else {
      // El backend devolvió un código de respuesta no exitoso
      console.error(
        `Backend devolvió el código ${error.status}, ` +
        `cuerpo fue: ${error.error}`);
    }
    // Devuelve un observable con un mensaje de error para el usuario
    return throwError(
      'Algo malo pasó; por favor, inténtalo de nuevo más tarde.');
  }
}