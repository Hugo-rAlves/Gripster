import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SimplificacaoComponent } from './simplificacao.component';

describe('SimplificacaoComponent', () => {
  let component: SimplificacaoComponent;
  let fixture: ComponentFixture<SimplificacaoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SimplificacaoComponent]
    });
    fixture = TestBed.createComponent(SimplificacaoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
