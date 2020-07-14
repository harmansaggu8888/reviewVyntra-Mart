import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Partner } from './partner';
@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private baseUrl = 'http://localhost:8080/api/';

  constructor(private http:HttpClient) { }

  createCategory(category: object): Observable<object> {
    return this.http.post(`${this.baseUrl}`, category);
  }

  getCategoryList(): Observable<any> {
    return this.http.get(`${this.baseUrl}`+'category-list');
  }

  deleteCategory(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/category-delete/${id}`, { responseType: 'text' });
  }
}
