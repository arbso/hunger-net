import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageMenusComponent } from './manage-menus.component';

describe('ManageMenusComponent', () => {
  let component: ManageMenusComponent;
  let fixture: ComponentFixture<ManageMenusComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManageMenusComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageMenusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
