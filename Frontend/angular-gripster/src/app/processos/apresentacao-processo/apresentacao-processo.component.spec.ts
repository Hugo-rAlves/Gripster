import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApresentacaoProcessoComponent } from './apresentacao-processo.component';

describe('ApresentacaoProcessoComponent', () => {
  let component: ApresentacaoProcessoComponent;
  let fixture: ComponentFixture<ApresentacaoProcessoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ApresentacaoProcessoComponent]
    });
    fixture = TestBed.createComponent(ApresentacaoProcessoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
