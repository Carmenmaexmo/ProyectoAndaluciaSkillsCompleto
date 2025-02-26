import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaParticipantesAnonimoComponent } from './lista-participantes-anonimo.component';

describe('ListaParticipantesAnonimoComponent', () => {
  let component: ListaParticipantesAnonimoComponent;
  let fixture: ComponentFixture<ListaParticipantesAnonimoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListaParticipantesAnonimoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListaParticipantesAnonimoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
