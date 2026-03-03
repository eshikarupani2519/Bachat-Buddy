import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { finalize, Observable,of } from 'rxjs';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent {
  // isLoading: Observable<boolean>= of(false);
  isLoading=false;
  constructor(private authService:AuthService){
    
  }

regFormGroup=new FormGroup({
  email:new FormControl('',[Validators.required,Validators.email])
})
getFormControl(name:string){
  return this.regFormGroup.get(name);
}
isFormControlError(name:string){
  return this.getFormControl(name)?.errors?.['required'] && this.getFormControl(name)?.dirty
}
 submitData(){
    console.log(this.regFormGroup.value)
    if (this.regFormGroup.invalid ) {
      alert('Please fill all fields correctly');
      return;
    }
const userData = this.regFormGroup.value;
this.isLoading=true;
    // ✅ Send data to backend using service
    this.authService.forgotPassword(userData).pipe(finalize(() => this.isLoading = false)).subscribe({
      
      next: (response:any) => {
        console.log( response);
        alert(response.body);
        this.regFormGroup.reset();
      },
      error: (err:any) => {
        console.error( err);
        alert(err.error?.body);
      },
    });
  }
}
