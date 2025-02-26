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
  mensaje: string = '';  
  mensajeTipo: string = '';  
  especialidadId: number | null = null;  

  constructor(
    private participanteService: ParticipanteService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    const especialidadIdStr = this.authService.getEspecialidadFromToken();  
    if (especialidadIdStr !== null) {
      this.especialidadId = Number(especialidadIdStr);
      if (!isNaN(this.especialidadId)) {
        this.participanteService.obtenerParticipantesPorEspecialidad(this.especialidadId).subscribe(
          (data: ParticipanteDTO[]) => {
            this.participantes = data.map(participante => ({
              ...participante,
              especialidadId: participante.especialidadId || 0
            }));
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

  // VALIDACIONES COMUNES
  private validarDatos(nombre: string, apellidos: string, centro: string): boolean {
    const soloLetrasRegex = /^[A-Za-zÁáÉéÍíÓóÚúÑñ\s]+$/;
    const apellidosRegex = /^[A-Za-zÁáÉéÍíÓóÚúÑñ]+\s[A-Za-zÁáÉéÍíÓóÚúÑñ]+$/;

    if (!soloLetrasRegex.test(nombre.trim())) {
      this.mostrarMensaje('El nombre solo debe contener letras.', 'error');
      return false;
    }

    if (!apellidosRegex.test(apellidos.trim())) {
      this.mostrarMensaje('Debe poner dos apellidos.', 'error');
      return false;
    }

    if (!soloLetrasRegex.test(centro.trim())) {
      this.mostrarMensaje('El centro solo debe contener letras.', 'error');
      return false;
    }

    return true;
  }

  agregarParticipante(): void {
    if (!this.validarDatos(this.nuevoParticipante.nombre, this.nuevoParticipante.apellidos, this.nuevoParticipante.centro)) {
      return;
    }

    if (this.especialidadId !== null) {
      this.nuevoParticipante.especialidadId = this.especialidadId;
      this.participanteService.agregarParticipante(this.nuevoParticipante).subscribe(
        (participante: ParticipanteDTO) => {
          this.participantes.push(participante);
          this.nuevoParticipante = { id: 0, nombre: '', apellidos: '', centro: '', especialidadId: 0 };
          this.mostrarMensaje('Participante agregado exitosamente', 'success');
        },
        (error) => {
          console.error('Error adding participante:', error);
          this.mostrarMensaje('Error al agregar el participante', 'error');
        }
      );
    }
  }

  eliminarParticipante(idParticipante: number): void {
    this.participanteService.eliminarParticipante(idParticipante).subscribe(
      () => {
        this.participantes = this.participantes.filter(p => p.idParticipante !== idParticipante);
        this.mostrarMensaje('Participante eliminado exitosamente', 'success');
      },
      (error) => {
        console.error('Error deleting participante:', error);
        this.mostrarMensaje('No se ha podido eliminar el participante ya que tiene relaciones con otras tablas', 'error');
      }
    );
  }

  actualizarParticipante(participante: ParticipanteDTO): void {
    if (!this.validarDatos(participante.nombre, participante.apellidos, participante.centro)) {
      return;
    }

    const especialidadIdStr = this.authService.getEspecialidadFromToken();  
    const especialidadId = Number(especialidadIdStr);  

    const participanteUpdate: ParticipanteDTO = {
      idParticipante: participante.idParticipante,
      nombre: participante.nombre,
      apellidos: participante.apellidos,
      centro: participante.centro,
      especialidadId: especialidadId
    };

    this.participanteService.actualizarParticipante(participanteUpdate).subscribe(
      (updatedParticipante: ParticipanteDTO) => {
        const index = this.participantes.findIndex(p => p.idParticipante === updatedParticipante.idParticipante);
        if (index !== -1) {
          this.participantes[index] = updatedParticipante;
          this.mostrarMensaje('Participante actualizado exitosamente', 'success');
          this.participanteEditando = null;
        }
      },
      (error) => {
        console.error('Error updating participante:', error);
        this.mostrarMensaje('Error al actualizar el participante', 'error');
      }
    );
  }

  cancelarEdicion(): void {
    this.participanteEditando = null;
  }

  private mostrarMensaje(texto: string, tipo: string): void {
    this.mensaje = texto;
    this.mensajeTipo = tipo;
    console.log(`Mensaje: ${texto} | Tipo: ${tipo}`);
    setTimeout(() => {
      this.mensaje = '';
      this.mensajeTipo = '';
    }, 3000);
  }
}
