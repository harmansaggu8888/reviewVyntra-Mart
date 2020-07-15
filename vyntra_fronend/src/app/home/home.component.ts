import { Component, OnInit, ViewChild } from '@angular/core';
import { ApiService } from 'src/app/Service/api.service';
import { Product } from 'src/app/Model/product';
import { Router } from '@angular/router';



@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  products: Product[] = [];
  private auth_token: string;
  //data=[];  
  data=[{productid: 1, description: 'highly costly', price:1200, productname: 'shoe', quantity: '10', productimage: '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'}];

  constructor(private api: ApiService,private router: Router) { }

  ngOnInit(): void {

    this.api.getProductsWithoutAuth().subscribe(
      res => {
        this.products = res.oblist;
      }
    );

    /*
   if (this.api.isAuthenticated()) {
      this.auth_token = this.api.getToken();
      this.api.getProducts(this.auth_token).subscribe(
        res => {
          this.products = res.oblist;
        }
      );
    }
    */
  }

  addToCart(prodid) {
    
    if (this.api.isAuthenticated()){
      this.auth_token = this.api.getToken();
      this.api.addCartItems(prodid.value, this.auth_token).subscribe(res => {
        console.log(res);
      })
    }
    else
    {
      this.router.navigate(['/login']);
    }
    
  }

}
