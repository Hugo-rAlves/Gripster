import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProcessoPageComponent } from './processo-page/processo-page.component';
import { QuadroGeralComponent } from './quadro-geral/quadro-geral.component';
import GeneralInformation from '../shared/model/quadro-geral-processo-model';
import { UtilsModule } from '../utils/utils.module';
import { QuadroTextoComponent } from './quadro-texto/quadro-texto.component';
import { SimplificacaoComponent } from './simplificacao/simplificacao.component';



@NgModule({
  declarations: [
    ProcessoPageComponent,
    QuadroGeralComponent,
    QuadroTextoComponent,
    SimplificacaoComponent
  ],
  imports: [
    CommonModule,
    UtilsModule
  ],
  exports: [
    ProcessoPageComponent,
    QuadroGeralComponent
  ]
})
export class ProcessoModule { }
