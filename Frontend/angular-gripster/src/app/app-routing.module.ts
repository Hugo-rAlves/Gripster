import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProcessoPageComponent } from './processo/processo-page/processo-page.component';
import { ProcessosGestorPageComponent } from './processos/processos-gestor-page/processos-gestor-page.component';

const routes: Routes = [
  {
    path:'pagina',
    component: ProcessoPageComponent
  },
  {path:'processos-gestor/:valor', component:ProcessosGestorPageComponent},
  {path:'processo/:valor', component:ProcessoPageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
