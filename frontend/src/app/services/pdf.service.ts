import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PdfService {

  private apiUrl = 'http://localhost:8080/pdf/generar'; // Cambia la URL si es diferente

  constructor(private http: HttpClient) {}

  // Método para generar el PDF
  generarPDF(datosEvaluacion: any) {
    return this.http.post(this.apiUrl, datosEvaluacion, { responseType: 'blob' });
  }
}
