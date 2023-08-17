import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuadroGeralComponent } from './quadro-geral.component';

describe('QuadroGeralComponent', () => {
  let component: QuadroGeralComponent;
  let fixture: ComponentFixture<QuadroGeralComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [QuadroGeralComponent]
    });
    fixture = TestBed.createComponent(QuadroGeralComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
