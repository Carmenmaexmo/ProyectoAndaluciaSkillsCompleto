import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  imports: [CommonModule, FormsModule]
})
export class LoginComponent {
  username = '';
  password = '';
  errorMessage: string = '';  // Variable para el mensaje de error

  constructor(private authService: AuthService, private router: Router) {}

  onLogin() {
    this.errorMessage = '';  // Limpiar mensaje de error previo

    this.authService.login({ username: this.username, password: this.password }).subscribe(
      (response: any) => {
        if (response.token) {
          localStorage.setItem('token', response.token);
          console.log('Login exitoso:', response.token);
          this.router.navigate(['/navbar']);
        } else {
          this.errorMessage = 'Ocurrió un error al procesar la respuesta. Intenta nuevamente.';
        }
      },
      (error: any) => {
        console.error('Error en login:', error);
        if (error.error && error.error.error) {
          this.errorMessage = error.error.error;  // Muestra el error detallado
        } else {
          this.errorMessage = 'Usuario o contraseña incorrectos.';  // Mensaje genérico de error
        }
      }
    );
  }
}