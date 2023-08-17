import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuadroTextoComponent } from './quadro-texto.component';

describe('QuadroTextoComponent', () => {
  let component: QuadroTextoComponent;
  let fixture: ComponentFixture<QuadroTextoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [QuadroTextoComponent]
    });
    fixture = TestBed.createComponent(QuadroTextoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
