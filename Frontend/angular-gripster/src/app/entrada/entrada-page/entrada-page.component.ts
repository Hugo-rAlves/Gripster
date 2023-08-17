import { Component } from '@angular/core';
import { EntradaService } from '../entrada.service';

@Component({
  selector: 'app-entrada-page',
  templateUrl: './entrada-page.component.html',
  styleUrls: ['./entrada-page.component.css']
})
export class EntradaPageComponent {

  numProcessos:Number;
  numMunicipios:Number;
  numResponsaveis:Number;
  

  constructor(private service:EntradaService){

  }

  ngOnInit(){
    this.fetchNumProcessos();
    this.fetchNumMunicipios();
    this.fetchNumResponsaveis();
  }
  async fetchNumResponsaveis() {
    this.numResponsaveis = await this.service.getNumResponsaveis();
  }
  async fetchNumMunicipios() {
    this.numMunicipios = await this.service.getNumMunicipios();
  }
  async fetchNumProcessos() {
    this.numProcessos = await this.service.getNumProcessos();
  }

}
