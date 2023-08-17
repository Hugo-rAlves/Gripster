import { Injectable } from '@angular/core';
import GeneralInformation from '../shared/model/quadro-geral-processo-model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProcessoService {
  // url:String;
  url = 'http://localhost:8080/api/v1/app/';
  constructor(private http: HttpClient) { }

  async getGeneralInformation(idProcesso: String): Promise<GeneralInformation>{
    const urlEndpoint = this.url +'get-informacoes-gerais-processo' + '/' + idProcesso;
    let generalInformation = this.http.get<GeneralInformation>(urlEndpoint).toPromise()
    return generalInformation;
  }

}
