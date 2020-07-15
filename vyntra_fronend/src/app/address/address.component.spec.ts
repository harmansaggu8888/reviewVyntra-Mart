import { async, ComponentFixture, TestBed,  } from '@angular/core/testing';
import { Component, OnInit } from '@angular/core';

import { Address } from 'src/app/Model/address';
import { ApiService } from 'src/app/Service/api.service';
import { Router } from '@angular/router';

import { AddressComponent } from './address.component';

describe('AddressComponent', () => {
  let component: AddressComponent;
  let fixture: ComponentFixture<AddressComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddressComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddressComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
