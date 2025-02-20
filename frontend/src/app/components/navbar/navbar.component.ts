import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';  // Asegúrate de importar el AuthService
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
  standalone: true,
  imports: [CommonModule, RouterLink]  // Importa CommonModule y RouterLink aquí
})

export class NavbarComponent implements OnInit {
  role: string | null = null;
  username: string | null = null;
  isAuthenticated: boolean = false;

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    // Verifica si el usuario está autenticado
    this.isAuthenticated = this.authService.isAuthenticated();
    if (this.isAuthenticated) {
      // Obtén el rol y el nombre del usuario del token cuando se carga el componente
      this.role = this.authService.getRoleFromToken();
      this.username = this.authService.getUsernameFromToken(localStorage.getItem('token') || '');
      console.log('Rol del usuario:', this.role);  // Agrega este mensaje de consola
    }
  }

  // Método para hacer logout
  logout() {
    this.authService.logout();  // Llama al método logout del AuthService
    window.location.reload();  // Recarga la página para aplicar el cambio
  }
}