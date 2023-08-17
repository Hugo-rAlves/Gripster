import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcessosMunicipioPageComponent } from './processos-municipio-page.component';

describe('ProcessosMunicipioPageComponent', () => {
  let component: ProcessosMunicipioPageComponent;
  let fixture: ComponentFixture<ProcessosMunicipioPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProcessosMunicipioPageComponent]
    });
    fixture = TestBed.createComponent(ProcessosMunicipioPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
