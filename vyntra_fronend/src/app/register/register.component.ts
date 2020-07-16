import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/app/Service/api.service';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
    public registerForm: any;
    private userRegistered: boolean;


    constructor(private apiService: ApiService,
      private router: Router,
      private formBuilder: FormBuilder) 
    {
      this.createForm();
      this.userRegistered = false;
    }

    ngOnInit() {
    }



    createForm() {
      this.registerForm = this.formBuilder.group({
        email: '',
        password: '',
        username: '',
        age: '',
        usertype: 'customer'
      });
    }


    register(): void {
      this.apiService.register(this.registerForm.value).subscribe(res => {
          if (res.status == "400") {
            console.log("Details cannot be empty");
          } else {
            this.userRegistered = true;
            this.router.navigate(['/register_user']);
          }
        },
          err => {
            alert("An error has occured, Please try again !!!");
          });
    }


    isRegistered(){
      if(this.userRegistered){
        this.userRegistered = false;
        return true;
      }
      else{
        return false;
      }
    }  

}

