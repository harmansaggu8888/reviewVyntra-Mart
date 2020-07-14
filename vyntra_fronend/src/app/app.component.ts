import { Component, OnInit,ViewChild } from '@angular/core';
import { ApiService } from 'src/app/Service/api.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Cart } from 'src/app/Model/cart';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'vyntramart';
  @ViewChild('closebutton') closebutton;
  cartlist: Cart[];
  private auth: string;
    public loginForm: FormGroup;
    error = false;
    constructor(private apiService: ApiService,
      private router: Router,
      private formBuilder: FormBuilder) {
      
    }
  
    ngOnInit() {
      this.createForm();
      this.auth = this.apiService.getToken();
      this.apiService.getCartItems(this.auth).subscribe(res => {
        this.cartlist = res.oblist;
      });

    }
    createForm() {
      this.loginForm = this.formBuilder.group({
        email: '',
        password: '',
        username: '',
        age: '',
        usertype: 'customer'
      });
    }
    login(): void {
      this.apiService.userLogin(this.loginForm.value).
        subscribe(res => {
          if (res.status == "200") {
            this.apiService.storeToken(res.auth_TOKEN, "customer");
            this.router.navigate(['/Home']);
            this.error = false;
            this.closebutton.nativeElement.click();
          }
          /*
          else if (res.status == "500") {
            this.apiService.adminLogin(this.loginForm.value).
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

    isLoggedIn() {
      if (this.apiService.getToken() != null)  {
        console.log("The user is logged in")
        return true;
      }
      else {
        return false;
      }
    }


    isAdminLoggedIn() {
      const user = this.apiService.getAuthType();
      if ( user != null)  {
          if (user == 'admin'){
            return true;
          }
      }
      return false;
    }


    logout() {
      this.apiService.removeToken()
      this.router.navigate(['/Home']);
    }


  }
  