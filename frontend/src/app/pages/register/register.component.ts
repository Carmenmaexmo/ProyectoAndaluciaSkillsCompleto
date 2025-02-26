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
    role: 'EXPERTO',
    especialidadId: null
  };

  especialidades: any[] = [];
  errores = { username: '', password: '', nombre: '', apellidos: '', dni: '' };
  errorMessage: string = '';
  successMessage: string = '';

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
      this.especialidades = data.map(especialidad => ({
        idEspecialidad: especialidad.id_especialidad,
        nombre: especialidad.nombre
      }));
    });
  }

  validarUsername() {
    if (!this.user.username.trim()) {
      this.errores.username = "El nombre de usuario es obligatorio.";
      return;
    }

    this.authService.verificarUsername(this.user.username).subscribe((existe: boolean) => {
      this.errores.username = existe ? "El nombre de usuario ya está ocupado." : "";
    });
  }

  validarPassword() {
    const password = this.user.password;
    if (!password.trim()) {
      this.errores.password = "La contraseña es obligatoria.";
    } else if (!/(?=.*[A-Za-z])(?=.*\d)/.test(password)) {
      this.errores.password = "Debe contener al menos una letra y un número.";
    } else {
      this.errores.password = "";
    }
  }

  validarNombre() {
    const regex = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/;
    this.errores.nombre = regex.test(this.user.nombre) ? "" : "Solo se permiten letras y espacios.";
  }

  validarApellidos() {
    const regex = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/;
    this.errores.apellidos = regex.test(this.user.apellidos) ? "" : "Solo se permiten letras y espacios.";
  }

  validarDNI() {
    const regex = /^\d{8}[A-Za-z]$/;
    if (!regex.test(this.user.dni)) {
      this.errores.dni = "Formato de DNI incorrecto (ej: 12345678X).";
      return;
    }

    const letras = "TRWAGMYFPDXBNJZSQVHLCKE";
    const numero = parseInt(this.user.dni.substring(0, 8), 10);
    const letraCorrecta = letras[numero % 23];

    if (this.user.dni.charAt(8).toUpperCase() !== letraCorrecta) {
      this.errores.dni = "DNI inválido.";
    } else {
      this.errores.dni = "";
    }
  }

  tieneErrores(): boolean {
    return Object.values(this.errores).some(error => error !== "");
  }

  onRegister() {
    this.errorMessage = '';
    this.successMessage = '';

    this.validarUsername();
    this.validarPassword();
    this.validarNombre();
    this.validarApellidos();
    this.validarDNI();

    if (this.tieneErrores()) return;

    this.authService.register(this.user).subscribe(
      (response: any) => {
        this.successMessage = response.message;
        setTimeout(() => {
          this.router.navigate(['/admin/experto']);
        }, 2000);
      },
      (error: any) => {
        this.errorMessage = error.error?.error || "⚠️ Error desconocido en el registro.";
      }
    );
  }

  cancelarRegistro() {
    this.router.navigate(['/admin/experto']);
  }
}
