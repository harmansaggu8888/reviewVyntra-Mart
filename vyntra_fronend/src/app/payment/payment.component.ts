import { Component, OnInit } from '@angular/core';
import { Address } from 'src/app/Model/address';
import { ApiService } from 'src/app/Service/api.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {
  
  
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
    this.auth = this.api.getToken();
    this.api.getAddress(this.auth).subscribe(res => {
      if (res.map != null) {
        this.model = res.map;
      }
    }, err => {
      console.log(err);
    });
  }


  track_order() {
    this.route.navigate(['/track_order']);
  }

  
}
