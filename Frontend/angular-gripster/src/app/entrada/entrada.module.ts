import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EntradaPageComponent } from './entrada-page/entrada-page.component';
import { UtilsModule } from '../utils/utils.module';



@NgModule({
  declarations: [
    EntradaPageComponent
  ],
  imports: [
    CommonModule,
    UtilsModule
  ]
})
export class EntradaModule { }
