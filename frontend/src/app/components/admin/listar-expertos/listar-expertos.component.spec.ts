import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarExpertosComponent } from './listar-expertos.component';

describe('ListarExpertosComponent', () => {
  let component: ListarExpertosComponent;
  let fixture: ComponentFixture<ListarExpertosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListarExpertosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListarExpertosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
