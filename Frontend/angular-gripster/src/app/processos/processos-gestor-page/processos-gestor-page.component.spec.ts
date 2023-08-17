import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcessosGestorPageComponent } from './processos-gestor-page.component';

describe('ProcessosGestorPageComponent', () => {
  let component: ProcessosGestorPageComponent;
  let fixture: ComponentFixture<ProcessosGestorPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProcessosGestorPageComponent]
    });
    fixture = TestBed.createComponent(ProcessosGestorPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
