import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarParticipantesComponent } from './listar-participantes.component';

describe('ListarParticipantesComponent', () => {
  let component: ListarParticipantesComponent;
  let fixture: ComponentFixture<ListarParticipantesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListarParticipantesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListarParticipantesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
