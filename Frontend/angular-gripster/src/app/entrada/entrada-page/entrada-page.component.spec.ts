import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EntradaPageComponent } from './entrada-page.component';

describe('EntradaPageComponent', () => {
  let component: EntradaPageComponent;
  let fixture: ComponentFixture<EntradaPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EntradaPageComponent]
    });
    fixture = TestBed.createComponent(EntradaPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
