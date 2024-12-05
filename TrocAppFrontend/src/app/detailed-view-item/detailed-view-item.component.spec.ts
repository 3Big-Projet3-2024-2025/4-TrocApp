import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailedViewItemComponent } from './detailed-view-item.component';

describe('DetailedViewItemComponent', () => {
  let component: DetailedViewItemComponent;
  let fixture: ComponentFixture<DetailedViewItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetailedViewItemComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DetailedViewItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
