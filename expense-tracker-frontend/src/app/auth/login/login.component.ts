import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

 usernameError:any=false;
 id!:number;
  constructor(private httpClient:HttpClient,private router:Router,private authService:AuthService){}

  // } this is against the convention to directly communicate with backend hence we take help of service
  // constructor(private registerService: RegisterService){

  
  //  ngOnInit(){
  //   this.subscribeToUserNameChanges();
  // }

regFormGroup=new FormGroup({
  email:new FormControl('',[Validators.required,Validators.email]),
  password:new FormControl('',[Validators.required])
})
getFormControl(name:string){
  return this.regFormGroup.get(name);
}
isFormControlError(name:string){
  return this.getFormControl(name)?.errors?.['required'] && this.getFormControl(name)?.dirty
}

 submitData(){
     
    if (this.regFormGroup.invalid) {
      alert('Please fill all fields correctly');
      return;
    }
  


const userData = this.regFormGroup.value;
console.log(userData)
    // ✅ Send data to backend using service
    this.authService.login(userData).subscribe({
      next: (response:any) => {
        console.log('User logged in:', response);
        // this.id=response.body;
        alert("Logged in successfully");
        // localStorage.setItem("id",this.id.toString())
        this.regFormGroup.reset();
        this.routeToPage('dashboard');
      },
      error: (err:any) => {
        console.error('Login error:', err);
       if(err.body) alert(err.body );
       else alert("some error occurred");
        // alert(err.error?.text );
      },
    });
  }
routeToPage(page:string){
this.router.navigate([page]);
}
  
}


