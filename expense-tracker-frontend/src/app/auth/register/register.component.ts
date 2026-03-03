import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  constructor(private authService: AuthService, private router: Router) { }
  regFormGroup = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    phone: new FormControl('', [Validators.required,Validators.pattern('^[0-9]{10}$')]),
    username: new FormControl('', [Validators.required]),
    name: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required]),
    confirmPassword: new FormControl('', [Validators.required]),
  })
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
  checkPhone(){
    return this.getFormControl('phone')?.value?.length!=10 
      && this.getFormControl('phone')?.dirty  &&
      this.getFormControl('phone')?.touched 
  }
  submitData() {
    console.log(this.regFormGroup.value)

    if (this.regFormGroup.invalid || this.checkPassword()) {
      alert('Please fill all fields correctly');
      return;
    }

    const userData = this.regFormGroup.value;
    this.authService.register(userData).subscribe({
      next: (response:any) => {
        console.log('User registered:', response);
        alert(response.body);
        this.regFormGroup.reset();
      },
      error: (err: any) => {
        console.error('Registration error:', err);
        alert(err.error.body||"registeration failed");
      },
    });
  }
}
