import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface EspecialidadDTO {
  idEspecialidad: number;
  nombre: string;
  codigo: string;
}

@Injectable({
  providedIn: 'root'
})
export class EspecialidadService {
  private apiUrl = 'http://localhost:8080/especialidades'; // URL del backend

  constructor(private http: HttpClient) {}

  obtenerEspecialidades(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  obtenerEspecialidad(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  agregarEspecialidad(especialidad: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, especialidad);
  }

  actualizarEspecialidad(id: number, especialidad: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, especialidad);
  }

  eliminarEspecialidad(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
  obterEspecialidadPorId(id: number): Observable<EspecialidadDTO> {
    return this.http.get<EspecialidadDTO>(`${this.apiUrl}/${id}`);
  }
}