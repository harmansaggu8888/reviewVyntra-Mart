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
  

  constructor(private api: ApiService,private router: Router) { 
    
  }

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
