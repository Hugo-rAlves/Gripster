import { Component, Input } from '@angular/core';
import { ProcessosApresentacaoService } from '../processos-apresentacao.service';
import ApresentaProcesso from 'src/app/shared/model/apresentacao-processo-model';

@Component({
  selector: 'app-apresentacao-processo',
  templateUrl: './apresentacao-processo.component.html',
  styleUrls: ['./apresentacao-processo.component.css']
})
export class ApresentacaoProcessoComponent {

  @Input()
  gestor:String;
  @Input()
  municipio:String;
  nome:String;

  apresentacoes: ApresentaProcesso[] = []
  

  constructor (private service: ProcessosApresentacaoService) {

  }

  ngOnInit() {
      this.fetchApresentacaoProcessos(this.gestor, this.municipio)
  }

  async fetchApresentacaoProcessos(gestor: String, municipio:String) {

    if(municipio === 'vazio' && gestor.length>0 ){
      this.apresentacoes = await this.service.getApresentacoesProcessosGestor(gestor);
      this.nome = gestor
    }
    else if (municipio.length > 0 && gestor === 'vazio'){
      this.apresentacoes = await this.service.getApresentacoesProcessosMunicipio(municipio);
      this.nome = municipio
    }
    console.log(this.apresentacoes);
  }
}
