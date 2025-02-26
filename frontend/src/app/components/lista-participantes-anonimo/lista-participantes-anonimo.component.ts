import { Component, OnInit } from '@angular/core';
import { ParticipanteDTO, ParticipanteService } from '../../services/participante.service';
import { EspecialidadService, EspecialidadDTO } from '../../services/especialidad.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-lista-participantes-anonimo',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './lista-participantes-anonimo.component.html',
  styleUrls: ['./lista-participantes-anonimo.component.css'],
})
export class ListaParticipantesAnonimoComponent implements OnInit {
  participantes: ParticipanteDTO[] = [];
  especialidades: Map<number, string> = new Map();

  constructor(
    private participanteService: ParticipanteService,
    private especialidadService: EspecialidadService
  ) {}

  ngOnInit(): void {
    this.participanteService.obtenerParticipantes().subscribe((participantes) => {
      this.participantes = participantes;
      console.log('Participantes obtenidos:', this.participantes);  // Depuración

      // Obtener los nombres de las especialidades para cada participante
      this.participantes.forEach(participante => {
        this.especialidadService.obterEspecialidadPorId(participante.especialidadId).subscribe((especialidad) => {
          this.especialidades.set(participante.especialidadId, especialidad.nombre);
        });
      });
    });
  }

  obtenerNombreEspecialidad(especialidadId: number): string {
    return this.especialidades.get(especialidadId) || 'Desconocida';
  }
}