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
  errores = { nombre: '', codigo: '' };

  constructor(private especialidadService: EspecialidadService) {}

  ngOnInit(): void {
    console.log("Cargando GestionarEspecialidadesComponent");
    this.cargarEspecialidades();
  }
 
  cargarEspecialidades() {
    this.especialidadService.obtenerEspecialidades().subscribe(data => {
      if (!Array.isArray(data)) {
        console.error("❌ ERROR: La respuesta no es un array.");
        return;
      }
      this.especialidades = data.map(especialidad => ({
        idEspecialidad: especialidad.id_especialidad,
        nombre: especialidad.nombre,
        codigo: especialidad.codigo
      }));
    }, error => console.error('❌ Error al obtener especialidades:', error));
  }

  validarNombre(editando = false) {
    const nombre = editando ? this.especialidadEditando.nombre : this.nuevaEspecialidad.nombre;
    if (!nombre.trim()) {
      this.errores.nombre = "El nombre es obligatorio.";
    } else if (!/^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/.test(nombre)) {
      this.errores.nombre = "El nombre solo puede contener letras y espacios.";
    } else {
      this.errores.nombre = "";
    }
  }

  validarCodigo(editando = false) {
    const codigo = editando ? this.especialidadEditando.codigo : this.nuevaEspecialidad.codigo;
    if (!codigo.trim()) {
      this.errores.codigo = "El código es obligatorio.";
    } else if (codigo.length !== 4) {
      this.errores.codigo = "El código debe tener exactamente 4 caracteres.";
    } else {
      this.errores.codigo = "";
    }
  }

  tieneErrores(): boolean {
    return !!(this.errores.nombre || this.errores.codigo);
  }

  agregarEspecialidad() {
    this.validarNombre();
    this.validarCodigo();
    if (this.tieneErrores()) return;

    this.especialidadService.agregarEspecialidad(this.nuevaEspecialidad).subscribe(() => {
      this.cargarEspecialidades();
      this.nuevaEspecialidad = { nombre: '', codigo: '' };
    });
  }

  editarEspecialidad(especialidad: any) {
    this.especialidadEditando = { ...especialidad };
  }

  actualizarEspecialidad() {
    this.validarNombre(true);
    this.validarCodigo(true);
    if (this.tieneErrores()) return;

    this.especialidadService.actualizarEspecialidad(this.especialidadEditando.idEspecialidad, this.especialidadEditando).subscribe(() => {
      this.cargarEspecialidades();
      this.especialidadEditando = null;
    });
  }

  eliminarEspecialidad(id: number) {
    if (confirm('¿Seguro que deseas eliminar esta especialidad?')) {
      this.especialidadService.eliminarEspecialidad(id).subscribe(() => this.cargarEspecialidades());
    }
  }
}
