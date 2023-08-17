import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApresentacaoProcessoComponent } from './apresentacao-processo/apresentacao-processo.component';
import { UtilsModule } from '../utils/utils.module';
import { ProcessosGestorPageComponent } from './processos-gestor-page/processos-gestor-page.component';
import { ProcessosMunicipioPageComponent } from './processos-municipio-page/processos-municipio-page.component';




@NgModule({
  declarations: [
    ApresentacaoProcessoComponent,
    ProcessosGestorPageComponent,
    ProcessosMunicipioPageComponent
  ],
  imports: [
    CommonModule,
    UtilsModule
  ],
  exports:[
    ApresentacaoProcessoComponent,

  ]
})
export class ProcessosModule { }
