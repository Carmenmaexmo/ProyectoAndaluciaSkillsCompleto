import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../services/user.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-listar-expertos',
  templateUrl: './listar-expertos.component.html',
  styleUrls: ['./listar-expertos.component.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule]
})
export class ListarExpertosComponent implements OnInit {
  expertos: any[] = [];

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.cargarExpertos();
  }

  cargarExpertos() {
    this.userService.obtenerExpertos().subscribe(data => {
      this.expertos = data;
    });
  }

  eliminarExperto(id: number) {
    if (confirm('¿Seguro que deseas eliminar este experto?')) {
      this.userService.eliminarExperto(id).subscribe(() => {
        this.cargarExpertos();
      });
    }
  }
}