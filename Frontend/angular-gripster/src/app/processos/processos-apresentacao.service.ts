import { Injectable } from '@angular/core';
import ApresentaProcesso from '../shared/model/apresentacao-processo-model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProcessosApresentacaoService {
 

  url = 'http://localhost:8080/api/v1/app/';

  constructor(private http: HttpClient){

  }
  async getApresentacoesProcessosGestor(gestor: String): Promise<ApresentaProcesso[]> {
    const urlEndpoint = this.url +'get-processos-gestor' + '/' + gestor;
    let apresentacoes = this.http.get<ApresentaProcesso[]>(urlEndpoint).toPromise()
    return apresentacoes;
  }
  async getApresentacoesProcessosMunicipio(municipio: String): Promise<ApresentaProcesso[]> {
    const urlEndpoint = this.url +'get-processos-municipio' + '/' + municipio;
    let apresentacoes = this.http.get<ApresentaProcesso[]>(urlEndpoint).toPromise()
    return apresentacoes;
  }


}
