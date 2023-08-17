import { Component, Input } from '@angular/core';
import { ProcessoService } from '../processo.service';
import TextoCompleto from 'src/app/shared/model/texto-completo-model';

@Component({
  selector: 'app-quadro-texto',
  templateUrl: './quadro-texto.component.html',
  styleUrls: ['./quadro-texto.component.css']
})
export class QuadroTextoComponent {
  @Input()
  processo_id:String;

  textoCompleto: TextoCompleto;

  constructor(private service:ProcessoService){

  }
  ngOnInit(): void{
    this.fetchTextoCompleto(this.processo_id)
  }
  async fetchTextoCompleto(processo_id:String) {
      this.textoCompleto = await this.service.getTextoCompleto(processo_id);
      console.log(this.textoCompleto)
  }
}
