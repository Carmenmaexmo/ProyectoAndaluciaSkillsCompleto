import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import{ jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080'; // URL del backend

  constructor(private http: HttpClient) {}

  register(user: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/register`, user);
  }

  login(user: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, user);
  }

  logout() {
    localStorage.removeItem('token');
  }

  getUsernameFromToken(token: string): string {
    try {
      const decoded: any = jwtDecode(token);
      return decoded.sub; // En el backend, `sub` es el username
    } catch (error) {
      console.error('Error al decodificar el token:', error);
      return '';
    }
  }

  // Método para obtener el rol desde el token
  getRoleFromToken(): string | null {
    const token = localStorage.getItem('token');
    if (token) {
      const decodedToken: any = jwtDecode(token);  // Decodifica el token
      console.log('Token decodificado:', decodedToken);  // Agrega este mensaje de consola
      return decodedToken.role;  // 'role' es el nombre del campo en el JWT donde se almacena el rol
    }
    return null;  
  }

  getEspecialidadFromToken(): string | null {
    const token = localStorage.getItem('token');
    if (token) {
      const decodedToken: any = jwtDecode(token);  // Decodifica el token
      console.log('Token decodificado:', decodedToken);  // Agrega este mensaje de consola
      return decodedToken.especialidadId;  // 'role' es el nombre del campo en el JWT donde se almacena el rol

    }
    return null;  
  }

  // Método para verificar si el usuario está autenticado
  isAuthenticated(): boolean {
    const token = localStorage.getItem('token');
    return !!token;  // Devuelve true si hay un token, false si no
  }
}