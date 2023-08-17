import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-processos-municipio-page',
  templateUrl: './processos-municipio-page.component.html',
  styleUrls: ['./processos-municipio-page.component.css']
})
export class ProcessosMunicipioPageComponent {

  valorSelecionado:String;
  constructor(private router: ActivatedRoute){}

  ngOnInit(){
    this.router.params.subscribe(params => {
      this.valorSelecionado = params['valor'];
      console.log('selecionando valor: ' + this.valorSelecionado);
    })
  }
}
