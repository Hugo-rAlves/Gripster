import { Component, Input } from '@angular/core';
import { ProcessosApresentacaoService } from '../processos-apresentacao.service';

@Component({
  selector: 'app-apresentacao-processo',
  templateUrl: './apresentacao-processo.component.html',
  styleUrls: ['./apresentacao-processo.component.css']
})
export class ApresentacaoProcessoComponent {

  @Input()
  gestor:String;

  

  constructor (private service: ProcessosApresentacaoService) {

  }

  ngOnInit() {
      this.fetchApresentacaoProcessos(this.gestor)
  }
  fetchApresentacaoProcessos(gestor: String) {
    
  }
}
