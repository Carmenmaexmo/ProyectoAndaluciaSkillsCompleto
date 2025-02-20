import { Component, OnInit } from '@angular/core';
import { EspecialidadService } from '../../../services/especialidad.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-gestionar-especialidades',
  templateUrl: './gestionar-especialidades.component.html',
  styleUrls: ['./gestionar-especialidades.component.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule]
})
export class GestionarEspecialidadesComponent implements OnInit {
  especialidades: any[] = [];
  nuevaEspecialidad = { nombre: '', codigo: '' };
  especialidadEditando: any = null;

  constructor(private especialidadService: EspecialidadService) {}

  ngOnInit(): void {
    console.log("Cargando GestionarEspecialidadesComponent");
    this.cargarEspecialidades();
  }
 
  cargarEspecialidades() {
    this.especialidadService.obtenerEspecialidades().subscribe(data => {
      console.log('🟢 Especialidades recibidas desde el backend:', data);
  
      if (!Array.isArray(data)) {
        console.error("❌ ERROR: La respuesta no es un array. Verifica el backend.");
        return;
      }
  
      // Mapeamos los datos correctamente
      this.especialidades = data.map(especialidad => ({
        idEspecialidad: especialidad.id_especialidad, // Verificar que este campo llega desde el backend
        nombre: especialidad.nombre,
        codigo: especialidad.codigo
      }));
  
      console.log('🔵 Especialidades procesadas para el frontend:', this.especialidades);
    }, error => {
      console.error('❌ Error al obtener especialidades:', error);
    });
  }
  
  

  agregarEspecialidad() {
    if (!this.nuevaEspecialidad.nombre || !this.nuevaEspecialidad.codigo) {
      alert("Todos los campos son obligatorios");
      return;
    }
    this.especialidadService.agregarEspecialidad(this.nuevaEspecialidad).subscribe(() => {
      this.cargarEspecialidades();
      this.nuevaEspecialidad = { nombre: '', codigo: '' };
    });
  }

  editarEspecialidad(especialidad: any) {
    this.especialidadEditando = { ...especialidad };
  }

  actualizarEspecialidad() {
    this.especialidadService.actualizarEspecialidad(this.especialidadEditando.idEspecialidad, this.especialidadEditando).subscribe(() => {
      this.cargarEspecialidades();
      this.especialidadEditando = null;
    });
  }

  eliminarEspecialidad(id: number) {
    console.log('ID de especialidad a eliminar:', id); // Agrega este mensaje de consola para depuración
    if (confirm('¿Seguro que deseas eliminar esta especialidad?')) {
      this.especialidadService.eliminarEspecialidad(id).subscribe(() => {
        this.cargarEspecialidades();
        console.log('Especialidad eliminada:', id);
      }, error => {
        console.error('Error al eliminar la especialidad:', error);
      });
    }
  }
}