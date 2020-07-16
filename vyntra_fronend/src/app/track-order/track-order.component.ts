import { Component, OnInit } from '@angular/core';
import { Address } from 'src/app/Model/address';
import { ApiService } from 'src/app/Service/api.service';
import { Router } from '@angular/router';
import { Order } from '../Model/Order';



@Component({
  selector: 'app-track-order',
  templateUrl: './track-order.component.html',
  styleUrls: ['./track-order.component.css']
})
export class TrackOrderComponent implements OnInit {

  orderlist: any[] = [];
  date: Date;

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
    this.date = new Date();
    this.date.setDate( this.date.getDate() + 4 );
    this.auth = this.api.getToken();
    this.getOrderList();
    this.api.getAddress(this.auth).subscribe(res => {
      if (res.map != null) {
        this.model = res.map;
      }
    }, err => {
      console.log(err);
    });
  }


  getOrderList() {
    this.api.getOrders(this.auth).subscribe(res => {
      this.orderlist = res.orderlist;
    });
  }

}
