import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class EntradaService {

  url = 'http://localhost:8080/api/v1/app/';

  getNumProcessos(): Number | PromiseLike<Number> {
    const urlEndpoint = this.url +'get-processos';
    let quantidade = this.http.get<Number>(urlEndpoint).toPromise()
    return quantidade;
  }
  getNumMunicipios(): Number | PromiseLike<Number> {
    const urlEndpoint = this.url +'get-numero-municipios';
    let quantidade = this.http.get<Number>(urlEndpoint).toPromise()
    return quantidade;
  }
  getNumResponsaveis(): Number | PromiseLike<Number> {
    const urlEndpoint = this.url +'get-numero-gestores';
    let quantidade = this.http.get<Number>(urlEndpoint).toPromise()
    return quantidade;
  }

  constructor(private http:HttpClient) { }


}
