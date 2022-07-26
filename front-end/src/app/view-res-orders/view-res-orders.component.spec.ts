import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewResOrdersComponent } from './view-res-orders.component';

describe('ViewResOrdersComponent', () => {
  let component: ViewResOrdersComponent;
  let fixture: ComponentFixture<ViewResOrdersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewResOrdersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewResOrdersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
