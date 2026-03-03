import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { CategoriesService } from '../categories.service';
import { Router } from '@angular/router';
import { filter } from 'rxjs';
interface Transaction {
  id:number,
  amount: number;
  dateOfTransaction: string;
  notes: string;
  transaction_type: string;
  category_name: string;
}
interface Filter{
category:string,
start:string,
end:string,
type:string,
column:string,
direction:string,
pageNo:number,
rowsPerPage:number
}
@Component({
  selector: 'app-transactions',
  templateUrl: './transactions.component.html',
  styleUrls: ['./transactions.component.css']
})

export class TransactionsComponent {
   isEditMode:boolean=false;
  idToEdit:number=-1;
  transactions: Transaction[] = [];
  filteredTransactions: Transaction[] = [];

  categories:any={
    income:[],expense:[]
  };

  showModal = false;
  showDates:boolean=false;
start="";
end="";
  filters={
    category:"",
start:"",
end:"",
type:"",
column:"",
direction:"",
pageNo:1,
rowsPerPage:100
  } ;
  

  constructor(private fb: FormBuilder,private categoriesService:CategoriesService,private router:Router) {}

  transactionForm=new FormGroup({
     
      
      category_id:new FormControl('',Validators.required),
      amount:new FormControl('',Validators.required),
      dateOfTransaction:new FormControl('',Validators.required),
      transaction_type:new FormControl('expense',Validators.required),
      notes:new FormControl('',Validators.required)
   } )
ngOnInit(){
 this.getTransactions();
  this.categoriesService.getCategories().subscribe({
    next:(res:any)=> {
      console.log(res);
      this.categories.income=res.body.filter((elem:any)=>elem.transaction_type=="income");
      this.categories.expense=res.body.filter((elem:any)=>elem.transaction_type=="expense");
      console.log(this.categories);
      
    },
    error:(err:any)=>{
      console.log(err);
    }
  })
}
showDateFields(){
  if(!this.showDates)
this.showDates=true;
  else this.showDates=false;
}
getTransactions(){
  this.categoriesService.getTransactions(this.filters).subscribe({
    next:(res:any)=>{
      console.log(res.body);
      this.transactions=res.body;
    },
    error:(err:any)=>{
      console.log(err);
    }
  })
}
  // ===== Modal Controls =====
  openModal() {
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
    this.transactionForm.reset();
  }

  // ===== Add Transaction =====
  addTransaction() {
    if (this.transactionForm.valid) {
      let type=this.categories.income.includes(this.transactionForm.get("transaction_type"))?"income":"expense";
      
      this.transactionForm.patchValue({transaction_type:type})
      const newTransaction: any = this.transactionForm.value;
      console.log(newTransaction);
      this.categoriesService.createTransaction(newTransaction).subscribe({
        next:(res:any)=>{
          console.log(res);

        },
        error:(err:any)=>{
          console.log(err);
        }
      })

      // this.applyFilters();

      this.closeModal();
    }
  }

  // ===== Filtering =====
  applyFilters() {
   this.categoriesService.getTransactions(this.filters).subscribe({
    next:(res:any)=>{
      console.log("after filtering:",res);
      this.transactions=res.body;
    },
    error:(err:any)=>{
      console.log(err);
    }
   })
  }
onFilterChangeOfDates(start:string|null, end:string|null){
if(start!=null)this.filters.start=start;
if(end!=null)this.filters.end=end;
this.applyFilters();
}
  onFilterChange(key: string, event: Event) {
  const value = (event.target as HTMLSelectElement).value;

  console.log("filter: ", key, ":", value);

  switch (key) {
    case "category":
      this.filters.category = value;
      break;
    case "type":
      this.filters.type = value;
      break;
  }

  this.applyFilters();
}
  onSort(col:any,dir:any){
    this.filters.column=col;
    this.filters.direction=dir;
    this.applyFilters();
  }
 
 
}
