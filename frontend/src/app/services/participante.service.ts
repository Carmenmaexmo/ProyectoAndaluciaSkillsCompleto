import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ParticipanteService {
  private apiUrl = 'http://localhost:8080/participantes/mejor-nota'; // URL del backend

  constructor(private http: HttpClient) {}

  obtenerMejoresNotas(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
}