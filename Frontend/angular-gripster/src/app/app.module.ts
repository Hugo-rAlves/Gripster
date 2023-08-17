import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProcessoModule } from './processo/processo.module';
import { HttpClientModule } from '@angular/common/http';

import { ProcessosModule } from './processos/processos.module';
import { ProcessosGestorPageComponent } from './processos/processos-gestor-page/processos-gestor-page.component';
import { ProcessoPageComponent } from './processo/processo-page/processo-page.component';
import { EntradaModule } from './entrada/entrada.module';

@NgModule({
  declarations: [
    AppComponent


  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ProcessoModule,
    HttpClientModule,
    ProcessosModule,
    EntradaModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
