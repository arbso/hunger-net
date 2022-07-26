import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InspectMenuComponent } from './inspect-menu.component';

describe('InspectMenuComponent', () => {
  let component: InspectMenuComponent;
  let fixture: ComponentFixture<InspectMenuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InspectMenuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InspectMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
