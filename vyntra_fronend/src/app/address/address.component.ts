import { Component, OnInit } from '@angular/core';
import { Address } from 'src/app/Model/address';
import { ApiService } from 'src/app/Service/api.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.css']
})
export class AddressComponent implements OnInit {

  private addressForm: any;
  isAddressAdded: boolean;
  showAdd = false;
  model: Address = {
    address: '',
    city: '',
    state: '',
    country: '',
    zipcode: '',
    phonenumber: ''

  };
  auth: string;
  constructor(private api: ApiService, private route: Router) { }

  ngOnInit() {
    this.isAddressAdded = false;
    this.auth = this.api.getToken();
    this.api.getAddress(this.auth).subscribe(res => {
      if (res.map != null) {
        this.model = res.map;
      }
    }, err => {
      console.log(err);
    });
  }

  addAddress() {
    this.api.upAddress(this.auth, this.model).subscribe(res => {
      console.log(res);
    });
    this.isAddressAdded = true;
  }


  show() {
    this.showAdd = true;
  }
  hide() {
    this.showAdd = false;
  }


  payment() {
    this.route.navigate(['/payment']);
  }

}
