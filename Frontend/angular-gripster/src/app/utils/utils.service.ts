import { Injectable } from '@angular/core';
import PessoaGestora from '../shared/model/pessoa-model';
import Municipio from '../shared/model/municipio-model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UtilsService {

  constructor(private http: HttpClient) { }

  async getPessoaGestoras(): Promise<PessoaGestora []>{
    const url = 'http://localhost:8080/api/v1/app/get-gestores';
    let pessoas = this.http.get<PessoaGestora[]>(url).toPromise()
    return pessoas;
  }
  async getMunicipios(): Promise<Municipio []>{
    const url = 'http://localhost:8080/api/v1/app/get-municipios';
    let municipios = this.http.get<Municipio[]>(url).toPromise()
    return municipios;
  }
}
