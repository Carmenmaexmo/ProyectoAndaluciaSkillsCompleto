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

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
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
      { path: 'gestionar-participantes', component: GestionarParticipantesComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }