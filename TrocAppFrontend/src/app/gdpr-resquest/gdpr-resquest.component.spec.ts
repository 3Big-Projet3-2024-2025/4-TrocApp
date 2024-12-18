import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GdprResquestComponent } from './gdpr-resquest.component';

describe('GdprResquestComponent', () => {
  let component: GdprResquestComponent;
  let fixture: ComponentFixture<GdprResquestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GdprResquestComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GdprResquestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
