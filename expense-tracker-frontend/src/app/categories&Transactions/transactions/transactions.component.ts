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
hasNextPage = true;
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
rowsPerPage:10
  } ;
  fileContent!: string;
  fileResponse!:any;

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
      this.transactions = res.body;

      // 🔥 core logic
      this.hasNextPage = this.transactions.length === this.filters.rowsPerPage;
    },
    error:(err:any)=>{
      console.log(err);
    }
  })
}
nextPage(){
  if(this.hasNextPage){
    this.filters.pageNo++;
    this.getTransactions();
  }
}

prevPage(){
  if(this.filters.pageNo > 1){
    this.filters.pageNo--;
    this.getTransactions();
  }
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
          console.log("added:"+res);

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
  //  this.categoriesService.getTransactions(this.filters).subscribe({
  //   next:(res:any)=>{
  //     console.log("after filtering:",res);
  //     this.transactions=res.body;
  //   },
  //   error:(err:any)=>{
  //     console.log(err);
  //   }
  //  })
  this.filters.pageNo = 1;  
  this.getTransactions();
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
 
  selectedFile!: File ;

onFileSelected(event: any): void {
   this.selectedFile = event.target.files[0];

  
}
  
  onUpload(): void {
    if (!this.selectedFile) return;

  // const reader = new FileReader();

  // reader.onload = () => {
  //   const text = reader.result as string;

  //   const lines = text.split(/\r?\n/).filter(line => line.trim() !== '');

  //   // First row → headers
  //   this.headers = lines[0].split(',');

  //   // Remaining rows
  //   this.rows = lines.slice(1).map(line => line.split(','));

  //   console.log(this.headers);
  //   console.log(this.rows);
  // };

  // reader.readAsText(this.selectedFile);
    this.categoriesService.bulkUpload(this.selectedFile).subscribe({
      next:(res:any)=>{
        console.log(res.body);
        this.fileResponse=res.body;
        
        this.getTransactions();
      }
    })
}
editTransaction(id:number){
  this.isEditMode=true;
  this.openModal();
 this.idToEdit=id;
 this.categoriesService.getTransactionById(id).subscribe({
  next:(res:any)=>{
    console.log(res.body);
    this.transactionForm.patchValue(res.body);
  },
  error:(err:any)=>{
    console.log(err.body);
  }
 })
 
}
submitEdittedTransaction(){
  let transData=this.transactionForm.value;
  this.categoriesService.editTransaction(this.idToEdit,transData).subscribe({
    next:(res:any)=>{
      console.log(res.body);
      alert(res.body);
      this.getTransactions();
    },
    error:(err:any)=>{
      console.log(err);
    }
  })
}
deleteTransaction(id:number){
  this.categoriesService.deleteTransaction(id).subscribe({
     next:(res:any)=>{
      console.log(res.body);
      alert(res.body);
      this.getTransactions();
    },
    error:(err:any)=>{
      console.log(err);
    }
  })
}
}
