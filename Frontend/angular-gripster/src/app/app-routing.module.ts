import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProcessoPageComponent } from './processo/processo-page/processo-page.component';
import { ProcessosGestorPageComponent } from './processos/processos-gestor-page/processos-gestor-page.component';
import { ProcessosMunicipioPageComponent } from './processos/processos-municipio-page/processos-municipio-page.component';
import { EntradaPageComponent } from './entrada/entrada-page/entrada-page.component';

const routes: Routes = [
  {path:'', component: EntradaPageComponent},
  {path:'processos-gestor/:valor', component:ProcessosGestorPageComponent},
  {path:'processos-municipio/:valor', component:ProcessosMunicipioPageComponent},
  {path:'processo/:valor', component:ProcessoPageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
