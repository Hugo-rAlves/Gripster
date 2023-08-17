import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-processo-page',
  templateUrl: './processo-page.component.html',
  styleUrls: ['./processo-page.component.css']
})
export class ProcessoPageComponent {
  valorSelecionado: String;

  constructor (private router:ActivatedRoute){}

  ngOnInit(){
    this.router.params.subscribe(params => {
      this.valorSelecionado = params['valor'];
      console.log('selecionando valor: ' + this.valorSelecionado);
    })
  }

}
