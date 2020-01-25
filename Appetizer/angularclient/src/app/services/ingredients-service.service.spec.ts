import { TestBed } from '@angular/core/testing';

import { IngredientsServiceService } from './ingredients-service.service';

describe('IngredientsServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: IngredientsServiceService = TestBed.get(IngredientsServiceService);
    expect(service).toBeTruthy();
  });
});
