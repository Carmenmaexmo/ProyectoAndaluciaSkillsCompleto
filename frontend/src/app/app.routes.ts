import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { ListaParticipantesComponent } from './components/admin/lista-participantes/lista-participantes.component';
import { NgModule } from '@angular/core';
import { AuthGuard } from './guards/auth.guard';
import { NavbarComponent } from './components/navbar/navbar.component';
import { GestionarEspecialidadesComponent } from './components/admin/gestionar-especialidades/gestionar-especialidades.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent, pathMatch: 'full' },
  { path: 'navbar', component: NavbarComponent, canActivate: [AuthGuard] },
  {
    path: 'admin',
    children: [
      { path: '', redirectTo: 'participantes', pathMatch: 'full' },
      { path: 'participantes', component: ListaParticipantesComponent },
      { path: 'especialidades', component: GestionarEspecialidadesComponent },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }