import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategoriesComponent } from './categories/categories.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TransactionsComponent } from './transactions/transactions.component';
import { SharedModule } from '../shared/shared.module';



@NgModule({
  declarations: [
    CategoriesComponent,
    TransactionsComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    FormsModule,ReactiveFormsModule,
    SharedModule
  ]
})
export class CategoriesModule { }
