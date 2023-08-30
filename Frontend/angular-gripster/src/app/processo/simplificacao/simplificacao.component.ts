import { Component, Input } from '@angular/core';
import CardDecisaoSimplificada from 'src/app/shared/model/simplificacao-model';
import { ProcessoService } from '../processo.service';

@Component({
  selector: 'app-simplificacao',
  templateUrl: './simplificacao.component.html',
  styleUrls: ['./simplificacao.component.css']
})
export class SimplificacaoComponent {

  @Input()
  processo_id:String;

  cardSimplificacao:CardDecisaoSimplificada;
  
  constructor(private service:ProcessoService){

  }
  ngOnInit(): void{
    this.fetchTextoCompleto(this.processo_id)
  }
  async fetchTextoCompleto(processo_id:String) {
      this.cardSimplificacao = await this.service.getSimplificacao(processo_id);
      console.log(this.cardSimplificacao)
  }

}
