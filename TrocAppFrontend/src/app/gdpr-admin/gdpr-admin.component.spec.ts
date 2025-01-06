import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GdprAdminComponent } from './gdpr-admin.component';

describe('GdprAdminComponent', () => {
  let component: GdprAdminComponent;
  let fixture: ComponentFixture<GdprAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GdprAdminComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GdprAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
