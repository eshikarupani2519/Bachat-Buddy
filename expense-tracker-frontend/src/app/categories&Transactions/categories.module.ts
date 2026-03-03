import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategoriesComponent } from './categories/categories.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TransactionsComponent } from './transactions/transactions.component';



@NgModule({
  declarations: [
    CategoriesComponent,
    TransactionsComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    FormsModule,ReactiveFormsModule
  ]
})
export class CategoriesModule { }
