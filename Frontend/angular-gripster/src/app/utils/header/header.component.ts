import { Component } from '@angular/core';
import Municipio from 'src/app/shared/model/municipio-model';
import PessoaGestora from 'src/app/shared/model/pessoa-model';
import { UtilsService } from '../utils.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})

export class HeaderComponent {
  municipios: Municipio[] = []
  pessoas: PessoaGestora[] = []

  constructor(private modService: UtilsService, private router: Router){}

  ngOnInit(): void{
    this.fetchPessoas();
    this.fetchMunicipios();

  }
  async fetchPessoas(): Promise<void> {
    this.pessoas = await this.modService.getPessoaGestoras();
    console.log(this.pessoas);

  }
  async fetchMunicipios(): Promise<void> {
    this.municipios = await this.modService.getMunicipios();
    console.log(this.municipios);

  }

  redirecionarGestor(event:any){
    const optionValue = event.target.value;
    this.router.navigate(['/processos-gestor', optionValue])
  }

}
