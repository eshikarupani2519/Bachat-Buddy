import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { ForgotPasswordComponent } from './auth/forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './auth/reset-password/reset-password.component';
import { CategoriesComponent } from './categories&Transactions/categories/categories.component';
import { TransactionsComponent } from './categories&Transactions/transactions/transactions.component';

const routes: Routes = [
  {path:"",component:LoginComponent,pathMatch:"full"},
  {
    path:"login",component:LoginComponent
  },
  {
    path:"register",component:RegisterComponent
  },
  {
    path:"forgotPassword",component:ForgotPasswordComponent
  },
  {
    path:"resetPassword/:token",component:ResetPasswordComponent
  },
  {
    path:"categories",component:CategoriesComponent
  },
  {
    path:"transactions",component:TransactionsComponent
  },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
