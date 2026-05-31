import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthModule } from './auth/auth.module';
import { CategoriesModule } from './categories&Transactions/categories.module';
import { TransactionsComponent } from './categories&Transactions/transactions/transactions.component';
import { SharedModule } from './shared/shared.module';
import { NavbarComponent } from './shared/navbar/navbar.component';

import { DashboardComponent } from './categories&Transactions/dashboard/dashboard.component';

@NgModule({
  declarations: [
    AppComponent,
   
    DashboardComponent,
    // NavbarComponent
  

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AuthModule,
    CategoriesModule,
    SharedModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
