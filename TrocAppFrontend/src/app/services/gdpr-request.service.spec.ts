import { TestBed } from '@angular/core/testing';

import { GdprRequestService } from './gdpr-request.service';

describe('GdprRequestService', () => {
  let service: GdprRequestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GdprRequestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
