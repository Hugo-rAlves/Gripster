import { TestBed } from '@angular/core/testing';

import { ProcessosApresentacaoService } from './processos-apresentacao.service';

describe('ProcessosApresentacaoService', () => {
  let service: ProcessosApresentacaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProcessosApresentacaoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
