import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ParticipanteService } from '../../../services/participante.service';

@Component({
  selector: 'app-lista-participantes',
  templateUrl: './lista-participantes.component.html',
  styleUrls: ['./lista-participantes.component.scss'],
  standalone: true,
  imports: [CommonModule]
})
export class ListaParticipantesComponent implements OnInit {
  participantes: any[] = [];

  constructor(private participanteService: ParticipanteService) {}

  ngOnInit(): void {
    this.participanteService.obtenerMejoresNotas().subscribe(data => {
      this.participantes = data;
    });
  }
}