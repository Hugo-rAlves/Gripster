import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProcessoModule } from './processo/processo.module';
import { HttpClientModule } from '@angular/common/http';
import { ProcessosGestorPageComponent } from './processos-gestor-page/processos-gestor-page.component';

@NgModule({
  declarations: [
    AppComponent,
    ProcessosGestorPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ProcessoModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
