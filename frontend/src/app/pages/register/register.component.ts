import { Component, OnInit } from '@angular/core';
import { EspecialidadService } from '../../services/especialidad.service';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule],
})
export class RegisterComponent implements OnInit {
  user = {
    username: '',
    password: '',
    nombre: '',
    apellidos: '',
    dni: '',
    role: 'EXPERTO',  // Establecer el rol por defecto como EXPERTO
    especialidadId: null
  };

  especialidades: any[] = [];

  errorMessage: string = '';  // Mensaje de error general
  successMessage: string = ''; // Mensaje de éxito

  constructor(
    private authService: AuthService,
    private especialidadService: EspecialidadService,
    private router: Router
  ) {}

  ngOnInit(): void {
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

  onRegister() {
    this.errorMessage = '';  // Limpiar mensaje de error previo
    this.successMessage = ''; // Limpiar mensaje de éxito previo

    console.log('Intentando registrar usuario con los siguientes datos:', this.user);
  
    // Realizamos la petición de registro al backend
    this.authService.register(this.user).subscribe(
      
      (response: any) => {
        this.successMessage = response.message;
        setTimeout(() => {
          this.router.navigate(['/admin/experto']); // Redirigir a gestionar expertos
        }, 2000);
      },
      (error: any) => {
        console.log('Error recibido:', error);  // Verifica el error
        console.log('Contenido de error.error:', error.error);  // Asegúrate de que error.error tiene lo esperado
  
        if (error.error && error.error.error) {
          this.errorMessage = error.error.error;  // Asigna el mensaje de error
          console.log('Error message asignado:', this.errorMessage);  // Verifica que errorMessage está correctamente asignado
        } else {
          this.errorMessage = "⚠️ Error desconocido en el registro.";  // Mensaje por defecto si no se proporciona un error específico
          console.log('Error message por defecto:', this.errorMessage);  // Verifica que errorMessage tiene el valor por defecto
        }
      }
    );
  }
}