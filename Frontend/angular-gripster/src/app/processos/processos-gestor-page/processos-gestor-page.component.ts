import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-processos-gestor-page',
  templateUrl: './processos-gestor-page.component.html',
  styleUrls: ['./processos-gestor-page.component.css']
})
export class ProcessosGestorPageComponent {
  valorSelecionado: String;

  constructor(private router:ActivatedRoute){}

  ngOnInit(){
    this.router.params.subscribe(params => {
      this.valorSelecionado = params['valor'];
      console.log('selecionando valor: ' + this.valorSelecionado);
    })
  }
  
}
