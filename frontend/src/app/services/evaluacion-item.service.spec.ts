import { TestBed } from '@angular/core/testing';

import { EvaluacionItemService } from './evaluacion-item.service';

describe('EvaluacionItemService', () => {
  let service: EvaluacionItemService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EvaluacionItemService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
