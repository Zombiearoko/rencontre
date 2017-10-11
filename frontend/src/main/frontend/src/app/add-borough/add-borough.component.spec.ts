import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddBoroughComponent } from './add-borough.component';

describe('AddBoroughComponent', () => {
  let component: AddBoroughComponent;
  let fixture: ComponentFixture<AddBoroughComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddBoroughComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddBoroughComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
