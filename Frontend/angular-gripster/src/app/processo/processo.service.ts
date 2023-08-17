import { Injectable } from '@angular/core';
import GeneralInformation from '../shared/model/quadro-geral-processo-model';
import { HttpClient } from '@angular/common/http';
import TextoCompleto from '../shared/model/texto-completo-model';

@Injectable({
  providedIn: 'root'
})
export class ProcessoService {

 

  url = 'http://localhost:8080/api/v1/app/';
  constructor(private http: HttpClient) { }

  async getGeneralInformation(idProcesso: String): Promise<GeneralInformation>{
    const urlEndpoint = this.url +'get-informacoes-gerais-processo' + '/' + idProcesso;
    let generalInformation = this.http.get<GeneralInformation>(urlEndpoint).toPromise()
    return generalInformation;
  }

  getTextoCompleto(processo_id: any): Promise<TextoCompleto> {
    const urlEndpoint = this.url +'get-texto-completo' + '/' + processo_id;
    let texto = this.http.get<TextoCompleto>(urlEndpoint).toPromise()
    return texto;
  }

}
