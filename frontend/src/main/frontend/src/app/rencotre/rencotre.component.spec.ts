import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RencotreComponent } from './rencotre.component';

describe('RencotreComponent', () => {
  let component: RencotreComponent;
  let fixture: ComponentFixture<RencotreComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RencotreComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RencotreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
