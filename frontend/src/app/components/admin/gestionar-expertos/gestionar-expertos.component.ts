import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../../services/user.service';
import { EspecialidadService } from '../../../services/especialidad.service';

@Component({
  selector: 'app-gestionar-expertos',
  templateUrl: './gestionar-expertos.component.html',
  styleUrls: ['./gestionar-expertos.component.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule]
})
export class GestionarExpertosComponent implements OnInit {
  expertos: any[] = [];
  especialidades: any[] = [];
  successMessage: string = '';
  errorMessage: string = '';
  expertoSeleccionado: any = null;  // Variable para almacenar el experto seleccionado para edición

  constructor(
    private userService: UserService,
    private especialidadService: EspecialidadService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cargarEspecialidades();
  }

  cargarEspecialidades() {
    this.especialidadService.obtenerEspecialidades().subscribe(data => {
      console.log('Especialidades recibidas desde el backend:', data);
      this.especialidades = data.map(especialidad => ({
        idEspecialidad: especialidad.id_especialidad, // Verificar que este campo llega desde el backend
        nombre: especialidad.nombre,
        codigo: especialidad.codigo
      }));
      this.cargarExpertos();
    }, error => {
      console.error('Error al obtener especialidades:', error);
    });
  }

  cargarExpertos() {
    this.userService.obtenerUsuariosPorRol('EXPERTO').subscribe(data => {
      this.expertos = data.map(experto => {
        const especialidad = this.especialidades.find(e => e.idEspecialidad === experto.especialidadId);
        return {
          ...experto,
          especialidadNombre: especialidad ? especialidad.nombre : 'N/A'
        };
      });
    }, error => {
      console.error('Error al obtener expertos:', error);
    });
  }

  editarExperto(experto: any) {
    this.expertoSeleccionado = { ...experto };  // Clonar el objeto para evitar modificar el original
  }

  guardarCambios() {
    if (this.expertoSeleccionado) {
      this.userService.actualizarUsuario(this.expertoSeleccionado.idUser, this.expertoSeleccionado).subscribe(() => {
        console.log('Experto actualizado con éxito');
        this.successMessage = 'Experto actualizado con éxito';
        this.cargarExpertos();
        this.ocultarMensajes();
        this.expertoSeleccionado = null;  // Limpiar el formulario de edición
      }, error => {
        console.error('Error al actualizar el experto:', error);
        this.errorMessage = 'No se puede actualizar el experto.';
        this.ocultarMensajes();
      });
    }
  }

  cancelarEdicion() {
    this.expertoSeleccionado = null;  // Limpiar el formulario de edición
  }

  eliminarExperto(id: number) {
    if (confirm('¿Seguro que deseas eliminar este experto?')) {
      this.userService.eliminarUsuario(id).subscribe(() => {
        console.log('Experto eliminado con éxito');
        this.successMessage = 'Experto eliminado con éxito';
        this.cargarExpertos();
        this.ocultarMensajes();
      }, error => {
        console.error('Error al eliminar el experto:', error);
        this.errorMessage = 'No se puede eliminar el experto porque tiene registros relacionados.';
        this.ocultarMensajes();
      });
    }
  }

  agregarExperto() {
    this.router.navigate(['/register']);
  }

  ocultarMensajes() {
    setTimeout(() => {
      this.successMessage = '';
      this.errorMessage = '';
    }, 5000); // Ocultar mensajes después de 5 segundos
  }
}