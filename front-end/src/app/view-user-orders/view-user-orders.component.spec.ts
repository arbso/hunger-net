import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewUserOrdersComponent } from './view-user-orders.component';

describe('ViewUserOrdersComponent', () => {
  let component: ViewUserOrdersComponent;
  let fixture: ComponentFixture<ViewUserOrdersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewUserOrdersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewUserOrdersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
