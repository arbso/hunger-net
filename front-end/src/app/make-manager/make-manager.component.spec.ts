import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MakeManagerComponent } from './make-manager.component';

describe('MakeManagerComponent', () => {
  let component: MakeManagerComponent;
  let fixture: ComponentFixture<MakeManagerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MakeManagerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MakeManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
