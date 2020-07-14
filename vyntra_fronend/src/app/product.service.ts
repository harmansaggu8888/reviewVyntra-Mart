import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class ProductService {

  private baseUrl = 'http://localhost:8080/api/';

  constructor(private http:HttpClient) { }

  getProductList(): Observable<any> {
    return this.http.get(`${this.baseUrl}`+'product-list');
  }

  createProduct(product: object): Observable<object> {
    return this.http.post(`${this.baseUrl}`+'save-product', product);
  }

  

  getProduct(id: number): Observable<Object> {
    return this.http.get(`${this.baseUrl}/product/${id}`);
  }
  
}                                           