import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/users'; // URL base del backend

  constructor(private http: HttpClient) {}

  obtenerExpertos(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/role/EXPERTO`);
  }

  eliminarExperto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}