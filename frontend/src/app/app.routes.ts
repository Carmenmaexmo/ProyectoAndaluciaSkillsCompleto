import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { ListaParticipantesComponent } from './components/admin/lista-participantes/lista-participantes.component';
import { NgModule } from '@angular/core';
import { AuthGuard } from './guards/auth.guard';
import { NavbarComponent } from './components/navbar/navbar.component';
import { GestionarEspecialidadesComponent } from './components/admin/gestionar-especialidades/gestionar-especialidades.component';
import { RegisterComponent } from './pages/register/register.component';
import { GestionarExpertosComponent } from './components/admin/gestionar-expertos/gestionar-expertos.component';
import { ListarParticipantesComponent } from './components/experto/listar-participantes/listar-participantes.component';
import { GestionarParticipantesComponent } from './components/experto/gestionar-participantes/gestionar-participantes.component';
import { GestionarPuntuacionesComponent } from './components/experto/gestionar-puntuaciones/gestionar-puntuaciones.component';
import { ListarEspecialidadesComponent } from './components/experto/listar-especialidades/listar-especialidades.component';
import { ListaParticipantesAnonimoComponent } from './components/lista-participantes-anonimo/lista-participantes-anonimo.component';

export const routes: Routes = [
  { path: '', redirectTo: 'lista-participante-anonimo', pathMatch: 'full' },
  { path: 'lista-participante-anonimo', component: ListaParticipantesAnonimoComponent, pathMatch: 'full' },
  { path: 'login', component: LoginComponent, pathMatch: 'full' },
  { path: 'register', component: RegisterComponent },
  { path: 'navbar', component: NavbarComponent, canActivate: [AuthGuard] },
  {
    path: 'admin',
    children: [
      { path: '', redirectTo: 'participantes', pathMatch: 'full' },
      { path: 'participantes', component: ListaParticipantesComponent },
      { path: 'especialidades', component: GestionarEspecialidadesComponent },
      { path: 'experto', component: GestionarExpertosComponent } 
    ]
  },
  { path: 'experto', 
    children: [
      { path: '', redirectTo: 'participantes', pathMatch: 'full' },
      { path: 'participantes', component: ListarParticipantesComponent },
      { path: 'gestionar-participantes', component: GestionarParticipantesComponent },
      { path: 'gestionar-puntuaciones', component: GestionarPuntuacionesComponent },
      { path: 'especialidades', component: ListarEspecialidadesComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }