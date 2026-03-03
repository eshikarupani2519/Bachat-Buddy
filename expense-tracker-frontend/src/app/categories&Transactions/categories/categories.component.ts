import { Component } from '@angular/core';
import { CategoriesService } from '../categories.service';
import { Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css']
})
export class CategoriesComponent {
  isEditMode:boolean=false;
  idToEdit:number=-1;
  isModalOpen:boolean=false;
  search:any="";
  categoryForm=new FormGroup({
   
    user_id:new FormControl(''),
    name:new FormControl('',Validators.required),
    description:new FormControl(''),
    icon_url:new FormControl(''),
    transaction_type:new FormControl('expense',Validators.required),
 } )
  categories:any[]=[];
  constructor(private categoriesService:CategoriesService,private router:Router){}
  ngOnInit(){
    this.getCategories();
  }
// modal
  openModal() {
   this.isModalOpen=true;
  }

  closeModal() {
     this.isModalOpen=false;
  }

// main crud
getCategories(){
  
  this.categoriesService.getCategories().subscribe({
    next:(res:any)=>{
      console.log(res.body);
     this.categories=res.body;
    },
    error:(err:any)=>{
      alert(err.body);
    }
  })
  
  
}
addCategory(){

  let storedId = localStorage.getItem("id");
  let id:number = storedId ? +storedId : 0;

  if(id){
    if(!this.isEditMode){
      //  add
    this.categoryForm.patchValue({user_id:id.toString()})
    
     let data=this.categoryForm.value;
     console.log(data);
  this.categoriesService.addCategory(data).subscribe({
    next:(res:any)=>{
      console.log(res.body);
    alert(res.body);
    },
    error:(err:any)=>{
       console.log(err);
      alert(err.body);
    }
  })
}else{
// edit
  
    this.categoryForm.patchValue({user_id:id.toString()})
     let data=this.categoryForm.value;
  this.categoriesService.editCategory(data,this.idToEdit).subscribe({
    next:(res:any)=>{
      console.log(res.body);
    alert(res.body);
    },
    error:(err:any)=>{
       console.log(err.body);
      alert(err.body);
    }
  })
}
  }
  else{
    alert("Please login first!");
    this.router.navigate(['login']);
  }
  
  

}
editCategory(cat_id:number){
 this.isEditMode=true;

 this.idToEdit=cat_id;
 this.categoriesService.getCategoryById(cat_id).subscribe({
  next:(res:any)=>{
    console.log(res.body);
    let data=res.body[0];
     
    this.categoryForm.patchValue({user_id:data.user_id,name:data.name,description:data.description,icon_url:data.icon_url,transaction_type:data.transaction_type});
    this.openModal();
  },
 })
  
  
}
deleteCategory(cat_id:number){

  let storedId = localStorage.getItem("id");
  let id:number = storedId ? +storedId : 0;
  if(id){
  this.categoriesService.deleteCategory(cat_id).subscribe({
    next:(res:any)=>{
      console.log(res.body);
    alert(res.body);
    },
    error:(err:any)=>{
       console.log(err.body);
      alert(err.body);
    }
  })
  }
  else{
    alert("Please login first!");
    this.router.navigate(['login']);
  }
}
}
