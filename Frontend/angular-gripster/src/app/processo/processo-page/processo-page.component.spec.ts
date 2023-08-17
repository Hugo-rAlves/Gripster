import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcessoPageComponent } from './processo-page.component';

describe('ProcessoPageComponent', () => {
  let component: ProcessoPageComponent;
  let fixture: ComponentFixture<ProcessoPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProcessoPageComponent]
    });
    fixture = TestBed.createComponent(ProcessoPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
