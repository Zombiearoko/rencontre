import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MemberSessionComponent } from './member-session.component';

describe('MemberSessionComponent', () => {
  let component: MemberSessionComponent;
  let fixture: ComponentFixture<MemberSessionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MemberSessionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MemberSessionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
