import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfimrAccountComponent } from './confimr-account.component';

describe('ConfimrAccountComponent', () => {
  let component: ConfimrAccountComponent;
  let fixture: ComponentFixture<ConfimrAccountComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfimrAccountComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfimrAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
