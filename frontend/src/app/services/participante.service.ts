import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// Definir la interfaz para ParticipantePuntuacion
export interface ParticipantePuntuacion {
  idParticipante: number;
  nombre: string;
  apellidos: string;
  enunciadoPrueba: string;
  notaFinal: number;
  sumaNotas: number;
  sumaPuntuacionMaxima: number;
  puntuacionTotal: string;
}

// Definir la interfaz para ParticipanteDTO
export interface ParticipanteDTO {
  idParticipante: number;
  nombre: string;
  apellidos: string;
  centro: string;
  especialidadId: number;
}

// Definir la interfaz para ParticipanteAddUpdateDTO
export interface ParticipanteAddUpdateDTO {
  id: number;
  nombre: string;
  apellidos: string;
  centro: string;
  especialidadId: number;
}

@Injectable({
  providedIn: 'root'
})
export class ParticipanteService {
  private apiUrl = 'http://localhost:8080/participantes'; // URL del backend

  constructor(private http: HttpClient) {}

  obtenerMejoresNotas(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/mejor-nota`);
  }

  obtenerPuntuacionesPorEspecialidad(especialidadId: number): Observable<ParticipantePuntuacion[]> {
    return this.http.get<ParticipantePuntuacion[]>(`${this.apiUrl}/especialidad/${especialidadId}/puntuaciones`);
  }

  obtenerParticipantesPorEspecialidad(especialidadId: number): Observable<ParticipanteDTO[]> {
    return this.http.get<ParticipanteDTO[]>(`${this.apiUrl}/especialidad/${especialidadId}`);
  }

  agregarParticipante(participante: ParticipanteAddUpdateDTO): Observable<ParticipanteDTO> {
    return this.http.post<ParticipanteDTO>(this.apiUrl, participante);
  }

  eliminarParticipante(idParticipante: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${idParticipante}`);
  }

  actualizarParticipante(participante: ParticipanteDTO): Observable<ParticipanteDTO> {
    return this.http.put<ParticipanteDTO>(`${this.apiUrl}/${participante.idParticipante}`, participante);
  }
}