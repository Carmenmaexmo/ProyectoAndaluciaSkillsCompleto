import { Component, OnInit } from '@angular/core';
import { ParticipanteService, ParticipanteDTO, ParticipanteAddUpdateDTO } from '../../../services/participante.service';
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
  nuevoParticipante: ParticipanteAddUpdateDTO = { id: 0, nombre: '', apellidos: '', centro: '', especialidadId: 0 };
  participanteEditando: ParticipanteDTO | null = null;
  mensaje: string = '';  // Variable para almacenar el mensaje
  mensajeTipo: string = '';  // Variable para almacenar el tipo de mensaje ('success' o 'error')
  especialidadId: number | null = null;  // Variable para almacenar el especialidadId del usuario logueado

  constructor(
    private participanteService: ParticipanteService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    const especialidadIdStr = this.authService.getEspecialidadFromToken();  // Obtén el especialidadId del token como cadena
    if (especialidadIdStr !== null) {
      this.especialidadId = Number(especialidadIdStr);  // Convierte el especialidadId a número
      if (!isNaN(this.especialidadId)) {
        this.participanteService.obtenerParticipantesPorEspecialidad(this.especialidadId).subscribe(
          (data: ParticipanteDTO[]) => {
            this.participantes = data.map(participante => ({
              ...participante,
              especialidadId: participante.especialidadId || 0 // Asegúrate de que especialidadId no sea undefined
            }));
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
    if (this.especialidadId !== null) {
      this.nuevoParticipante.especialidadId = this.especialidadId;
      this.participanteService.agregarParticipante(this.nuevoParticipante).subscribe(
        (participante: ParticipanteDTO) => {
          this.participantes.push(participante);
          this.nuevoParticipante = { id: 0, nombre: '', apellidos: '', centro: '', especialidadId: 0 };
          this.mensaje = 'Participante agregado exitosamente';
          this.mensajeTipo = 'success';
          this.ocultarMensaje();
        },
        (error) => {
          console.error('Error adding participante:', error);
          this.mensaje = 'Error al agregar el participante';
          this.mensajeTipo = 'error';
          this.ocultarMensaje();
        }
      );
    }
  }

  eliminarParticipante(idParticipante: number): void {
    this.participanteService.eliminarParticipante(idParticipante).subscribe(
      () => {
        this.participantes = this.participantes.filter(p => p.idParticipante !== idParticipante);
        this.mensaje = 'Participante eliminado exitosamente';
        this.mensajeTipo = 'success';
        this.ocultarMensaje();
      },
      (error) => {
        console.error('Error deleting participante:', error);
        this.mensaje = 'No se ha podido eliminar el participante ya que tiene relaciones con otras tablas';
        this.mensajeTipo = 'error';
        this.ocultarMensaje();
      }
    );
  }

  actualizarParticipante(participante: ParticipanteDTO): void {
    const especialidadIdStr = this.authService.getEspecialidadFromToken();  // Obtén el especialidadId del token como cadena
    const especialidadId = Number(especialidadIdStr);  // Usa el especialidadId del token

    console.log('especialidadId del token:', especialidadId);  // Verifica el valor del especialidadId

    const participanteUpdate: ParticipanteDTO = {
      idParticipante: participante.idParticipante,
      nombre: participante.nombre,
      apellidos: participante.apellidos,
      centro: participante.centro,
      especialidadId: especialidadId // Usar el especialidadId del token
    };
    console.log('Participante actualizando:', participanteUpdate);
    this.participanteService.actualizarParticipante(participanteUpdate).subscribe(
      (updatedParticipante: ParticipanteDTO) => {
        const index = this.participantes.findIndex(p => p.idParticipante === updatedParticipante.idParticipante);
        if (index !== -1) {
          this.participantes[index] = updatedParticipante;
          this.mensaje = 'Participante actualizado exitosamente';
          this.mensajeTipo = 'success';
          this.participanteEditando = null;
          this.ocultarMensaje();
        }
      },
      (error) => {
        console.error('Error updating participante:', error);
        this.mensaje = 'Error al actualizar el participante';
        this.mensajeTipo = 'error';
        this.ocultarMensaje();
      }
    );
  }

  cancelarEdicion(): void {
    this.participanteEditando = null;
  }

  private ocultarMensaje(): void {
    console.log('Ocultando mensaje en 3 segundos');
    setTimeout(() => {
      console.log('Mensaje ocultado');
      this.mensaje = '';
      this.mensajeTipo = '';
    }, 3000);  // Ocultar el mensaje después de 3 segundos
  }
}