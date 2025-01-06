import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewingItemsMapComponent } from './viewing-items-map.component';

describe('ViewingItemsMapComponent', () => {
  let component: ViewingItemsMapComponent;
  let fixture: ComponentFixture<ViewingItemsMapComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewingItemsMapComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ViewingItemsMapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
