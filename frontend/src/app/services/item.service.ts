import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface CrearItemDTO {
  descripcion: string;
  peso: number;
  gradosConsecucion: number;  
}

export interface ItemDTO {
  idItem: number;
  descripcion: string;
  peso: number;
  gradosConsecucion: number;
}

@Injectable({
  providedIn: 'root'
})
export class ItemService {
  private apiUrl = 'http://localhost:8080/items';  // Ajusta la URL según tu API

  constructor(private http: HttpClient) { }

  agregarItem(item: CrearItemDTO): Observable<ItemDTO> {
    return this.http.post<ItemDTO>(this.apiUrl, item);
  }

  obtenerItems(): Observable<ItemDTO[]> {
    return this.http.get<ItemDTO[]>(this.apiUrl);
  }
}