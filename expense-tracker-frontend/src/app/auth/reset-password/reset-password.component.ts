import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent {
showPage=false;
 constructor(private authService: AuthService, private router: Router,private activatedRoute:ActivatedRoute) { }
 token=this.activatedRoute.snapshot.paramMap.get('token');
  regFormGroup = new FormGroup({
    password: new FormControl('', [Validators.required]),
    confirmPassword: new FormControl('', [Validators.required])
  })
  ngOnInit(){
    if(this.token){
    this.authService.validateToken(this.token).subscribe({
      next:(res:any)=>{
        // agar token galat hai toh page bhi nahi khulna chahiyeeeeeeee
        this.showPage=true;
      },
      error:(err:any)=>{
        alert(err.error.body || "Session expired");
        this.router.navigate(['login'])
      }
    })
  }
  else return;
  }
  getFormControl(name: string) {
    return this.regFormGroup.get(name);
  }
  isFormControlError(name: string) {
    return this.getFormControl(name)?.errors?.['required'] && this.getFormControl(name)?.dirty
  }
  checkPassword() {
    return this.getFormControl('password')?.value != this.getFormControl('confirmPassword')?.value
      && this.getFormControl('password')?.dirty && this.getFormControl('confirmPassword')?.dirty &&
      this.getFormControl('password')?.touched && this.getFormControl('confirmPassword')?.touched
  }
  submitData() {
    
    
    if (this.regFormGroup.invalid || this.checkPassword()) {
      alert('Please fill all fields correctly');
      return;
    }

    const userData = this.regFormGroup.value;
    
    this.authService.resetPassword(userData,this.token).subscribe({
      next: (response:any) => {
        
        alert(response.body);
        this.regFormGroup.reset();
      },
      error: (err: any) => {
       
        alert(err.error?.body || "Failed to reset password");
      },
    });
  }
}
