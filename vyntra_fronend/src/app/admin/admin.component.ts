import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/app/Service/api.service';
import { NavigationExtras, Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Product } from 'src/app/Model/product';
import { HttpEventType, HttpResponse } from '@angular/common/http';


@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  products: Product[] = [];
  fileToUpload: File = null;
  showAdd = false;
  auth: string;
  public adminForm: FormGroup;
  error = false;
  imageUrl: string = "/assets/img/admin/noimage.png";

  isProductAdded: boolean;

  constructor(private api: ApiService, private router: Router,
    private formBuilder: FormBuilder) { 
      
    }




  ngOnInit() {
    this.createForm();
    this.isProductAdded = false;
    if (this.api.isAuthenticated) {
      this.auth = this.api.getToken();
      this.api.getProducts(this.auth).subscribe(
        res => {
          this.products = res.oblist;
        }
      );
    }
  }
  handleFileInput(file: FileList) {
    this.fileToUpload = file.item(0);
    var reader = new FileReader();
    reader.onload = (event: any) => {
      this.imageUrl = event.target.result;
    }
    reader.readAsDataURL(this.fileToUpload);
  }
  show() {
    this.showAdd = true;
  }
  hide() {
    this.showAdd = false;
  }
  addProd(desc, quan, price, prodname, image) {
    this.api.addProduct(this.auth, desc.value, quan.value, price.value, prodname.value, this.fileToUpload).subscribe(res => {
      this.products = res.oblist;
      this.isProductAdded = true;
      this.router.navigate(['/admin']);
      this.showAdd = false;
    });
  }

  isProductSaved(){
    if (this.isProductAdded)
    {
      this.isProductAdded = false;
      return true;
    }
    return false;
  }

  delProd(prodid) {

    this.api.delProduct(this.auth, prodid.value).subscribe(res => {
      this.products = res.oblist;
    });

  }
  edit(prodid) {
    let navigationExtras: NavigationExtras = {
      queryParams: {
        "user": prodid.value
      }
    };
    this.router.navigate(["admin/edit"], navigationExtras);
  }







  createForm() {
    this.adminForm = this.formBuilder.group({
      email: '',
      password: ''
    });
  }
  login(): void {
    this.api.adminLogin(this.adminForm.value).
      subscribe(res => {
        if (res.status == "200") {
          this.api.storeToken(res.auth_TOKEN, "admin");
          this.router.navigate(['/admin']);
          this.error = false;
        }
        /*
        else if (res.status == "500") {
          this.apiService.adminLogin(this.adminForm.value).
            subscribe(res => {
              if (res.status == "200") {
                this.apiService.storeToken(res.auth_TOKEN, "admin");
                this.router.navigate(['/admin']);
              } else {
                this.router.navigate(['/login']);
              }
              this.error = false;
            },
              err => {
                console.log(err);
              });
        }
        */
      },
        err => {
          console.log(err);
      });
  }


  isAdminLoggedIn() {
    const user = this.api.getAuthType();
    if ( user != null)  {
        if (user == 'admin'){
          console.log("The admin is logged in");
          return true;
        }
    }
    return false;
  }

}
