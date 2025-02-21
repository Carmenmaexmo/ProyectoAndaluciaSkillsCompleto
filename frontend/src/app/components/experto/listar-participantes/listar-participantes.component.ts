import { Component, OnInit } from '@angular/core';
import { ParticipantePuntuacion, ParticipanteService } from '../../../services/participante.service';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../services/auth.service';  // Importa AuthService

@Component({
  selector: 'app-listar-participantes',
  templateUrl: './listar-participantes.component.html',
  styleUrls: ['./listar-participantes.component.css'],
  imports: [CommonModule]
})
export class ListarParticipantesComponent implements OnInit {
  participantes: ParticipantePuntuacion[] = [];

  constructor(
    private participanteService: ParticipanteService,
    private authService: AuthService  // Inyecta AuthService
  ) { }

  ngOnInit(): void {
    const especialidadIdStr = this.authService.getEspecialidadFromToken();  // Obtén el especialidadId del token como cadena
    if (especialidadIdStr !== null) {
      const especialidadId = Number(especialidadIdStr);  // Convierte el especialidadId a número
      if (!isNaN(especialidadId)) {
        this.participanteService.obtenerPuntuacionesPorEspecialidad(especialidadId).subscribe(
          (data: ParticipantePuntuacion[]) => {
            this.participantes = data;
          },
          (error) => {
            console.error('Error fetching participantes:', error);
          }
        );
      } else {
        console.error('El especialidadId no es un número válido');
      }
    } else {
      console.error('No se pudo obtener el especialidadId del token');
    }
  }
}