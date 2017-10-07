import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SessionAdminComponent } from './session-admin.component';

describe('SessionAdminComponent', () => {
  let component: SessionAdminComponent;
  let fixture: ComponentFixture<SessionAdminComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SessionAdminComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SessionAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
