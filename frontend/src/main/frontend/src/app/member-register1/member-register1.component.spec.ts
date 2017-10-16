import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MemberRegister1Component } from './member-register1.component';

describe('MemberRegister1Component', () => {
  let component: MemberRegister1Component;
  let fixture: ComponentFixture<MemberRegister1Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MemberRegister1Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MemberRegister1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
