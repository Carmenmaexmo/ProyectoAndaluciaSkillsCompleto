import { Component, OnInit } from '@angular/core';
import { EspecialidadService, EspecialidadDTO } from '../../../services/especialidad.service';
import { AuthService } from '../../../services/auth.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-listar-especialidades',
  templateUrl: './listar-especialidades.component.html',
  styleUrls: ['./listar-especialidades.component.css'],
  imports: [CommonModule, FormsModule]
})
export class ListarEspecialidadesComponent implements OnInit {
  especialidades: EspecialidadDTO[] = [];
  especialidadUsuario: string = '';

  constructor(
    private especialidadService: EspecialidadService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    // Obtener la especialidad del usuario desde el token
    const especialidadIdStr = this.authService.getEspecialidadFromToken();
    if (especialidadIdStr) {
      const especialidadId = Number(especialidadIdStr);
      if (!isNaN(especialidadId)) {
        this.especialidadService.obterEspecialidadPorId(especialidadId).subscribe(especialidad => {
          this.especialidadUsuario = especialidad.nombre;
          console.log('Especialidad:', especialidad);
        });
      }
    }

    // Obtener todas las especialidades
    this.especialidadService.obtenerEspecialidades().subscribe(especialidades => {
      this.especialidades = especialidades;
    });
  }
}