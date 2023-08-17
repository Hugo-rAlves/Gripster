import { Component, Input } from '@angular/core';
import { ProcessoService } from '../processo.service';
import GeneralInformation from 'src/app/shared/model/quadro-geral-processo-model';


@Component({
  selector: 'app-quadro-geral',
  templateUrl: './quadro-geral.component.html',
  styleUrls: ['./quadro-geral.component.css']
})
export class QuadroGeralComponent {
  @Input()
  processo_id: String;

  informacaoGeral: GeneralInformation;
  valorSelecionado:String;

  constructor(private processoService: ProcessoService){

  }
  ngOnInit():void{
    this.fetchGeneralInformation();   
  }
  
  async fetchGeneralInformation():Promise<void>{
    this.informacaoGeral = await this.processoService.getGeneralInformation(this.processo_id);
    console.log(this.informacaoGeral);
  }

}
