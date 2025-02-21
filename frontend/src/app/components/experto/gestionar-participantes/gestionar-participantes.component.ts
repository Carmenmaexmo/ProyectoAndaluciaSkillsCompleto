import { Component, OnInit } from '@angular/core';
import { ParticipanteService, ParticipanteDTO } from '../../../services/participante.service';
import { AuthService } from '../../../services/auth.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-gestionar-participantes',
  templateUrl: './gestionar-participantes.component.html',
  styleUrls: ['./gestionar-participantes.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule]
})
export class GestionarParticipantesComponent implements OnInit {
  participantes: ParticipanteDTO[] = [];
  nuevoParticipante: ParticipanteDTO = { idParticipante: 0, nombre: '', apellidos: '', centro: '', especialidadId: 0 };

  constructor(
    private participanteService: ParticipanteService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    const especialidadIdStr = this.authService.getEspecialidadFromToken();  // Obtén el especialidadId del token como cadena
    if (especialidadIdStr !== null) {
      const especialidadId = Number(especialidadIdStr);  // Convierte el especialidadId a número
      if (!isNaN(especialidadId)) {
        this.participanteService.obtenerParticipantesPorEspecialidad(especialidadId).subscribe(
          (data: ParticipanteDTO[]) => {
            this.participantes = data;
            console.log('Participantes:', this.participantes);
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

  agregarParticipante(): void {
    const especialidadIdStr = this.authService.getEspecialidadFromToken();  // Obtén el especialidadId del token como cadena
    if (especialidadIdStr !== null) {
      const especialidadId = Number(especialidadIdStr);  // Convierte el especialidadId a número
      if (!isNaN(especialidadId)) {
        this.nuevoParticipante.especialidadId = especialidadId;
        this.participanteService.agregarParticipante(this.nuevoParticipante).subscribe(
          (participante: ParticipanteDTO) => {
            this.participantes.push(participante);
            this.nuevoParticipante = { idParticipante: 0, nombre: '', apellidos: '', centro: '', especialidadId: 0 };
          },
          (error) => {
            console.error('Error adding participante:', error);
          }
        );
      } else {
        console.error('El especialidadId no es un número válido');
      }
    }
  }


  eliminarParticipante(id: number): void {
    this.participanteService.eliminarParticipante(id).subscribe(
      () => {
        this.participantes = this.participantes.filter(p => p.idParticipante !== id);
      },
      (error) => {
        console.error('Error deleting participante:', error);
      }
    );
  }

  editarParticipante(participante: ParticipanteDTO): void {
    this.participanteService.actualizarParticipante(participante).subscribe(
      (updatedParticipante: ParticipanteDTO) => {
        const index = this.participantes.findIndex(p => p.idParticipante === updatedParticipante.idParticipante);
        if (index !== -1) {
          this.participantes[index] = updatedParticipante;
        }
      },
      (error) => {
        console.error('Error updating participante:', error);
      }
    );
  }
}