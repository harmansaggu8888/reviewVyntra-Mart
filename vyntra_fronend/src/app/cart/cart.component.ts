import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/app/Service/api.service';
import { Cart } from 'src/app/Model/cart';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { Product } from '../product';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  prod: Product;
  private auth: string;
  cartlist: Cart[];
  totalSum: number = 0;
  constructor(private api: ApiService, private route: Router) {

  }

  ngOnInit() {
    this.auth = this.api.getToken();
    this.api.getCartItems(this.auth).subscribe(res => {
      this.cartlist = res.oblist;
      this.cartlist.forEach(value => {
        this.totalSum = this.totalSum + (value.quantity * value.price);
      });
    });

  }
  update(id, quantity) {
    this.api.updateCart(this.auth, id.value, quantity.value).subscribe(res => {
      this.cartlist = res.oblist;
      this.cartlist.forEach(value => {
        this.totalSum = this.totalSum + (value.quantity * value.price);
      });
    });
  }
  delete(id) {
    this.api.delCart(this.auth, id.value).subscribe(res => {
      this.cartlist = res.oblist;
      this.cartlist.forEach(value => {
        this.totalSum = this.totalSum + (value.quantity * value.price);
      });
    });
  }

  place() {
    this.api.place(this.auth).subscribe(res => {
      console.log(res);
    });
    console.log("sgfsdgsdgdsfgdsgsdgsdf");
    this.route.navigate(['/payment']);
  }


  getProductByID(prodid) {
    if (this.api.isAuthenticated) {
      console.log("Got the product");
      this.auth = this.api.getToken();
      this.api.getProductbyID(this.auth,prodid).subscribe(
        res => {
          this.prod = res.ob;
        }
      );
    }
  }
}
