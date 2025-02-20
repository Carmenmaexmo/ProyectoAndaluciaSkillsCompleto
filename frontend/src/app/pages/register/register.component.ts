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
      this.especialidades = data;
    });
  }

  onRegister() {
    this.errorMessage = '';  // Limpiar mensaje de error previo
    this.successMessage = ''; // Limpiar mensaje de éxito previo
  
    // Realizamos la petición de registro al backend
    this.authService.register(this.user).subscribe(
      (response: any) => {
        this.successMessage = response.message;
        setTimeout(() => {
          this.router.navigate(['/login']);
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