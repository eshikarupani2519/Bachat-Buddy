import { HttpClient, HttpClientModule, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CategoriesService {
  private api_url="http://localhost:8080"
  constructor(private http:HttpClient) { }
  addCategory(category:any){
    return this.http.post(`${this.api_url}/categories`,category,{withCredentials:true});
  }
  editCategory(category:any,id:number){
    return this.http.put(`${this.api_url}/categories/${id}`,category,{withCredentials:true});
  }
  getCategories(){
    return this.http.get(`${this.api_url}/categories`,{withCredentials:true});
  }
  deleteCategory(id:number){
    return this.http.patch(`${this.api_url}/categories/${id}`,id,{withCredentials:true});
  }
  getCategoryById(id:number){
    return this.http.get(`${this.api_url}/category/${id}`,{withCredentials:true});
  }
  createTransaction(transaction:any){
    return this.http.post(`${this.api_url}/transactions`,transaction,{withCredentials:true});
  }
 getTransactions(filters: any) {
  let params = new HttpParams();

  if (filters) {
    Object.keys(filters).forEach(key => {
      const value = filters[key];

      //  Only append if meaningful value
      if (
        value !== null &&
        value !== undefined &&
        value !== '' &&
        !(typeof value === 'number' && value === 0)
      ) {
        params = params.append(key, value);
      }
    });
  }

 

  return this.http.get(`${this.api_url}/transactions`, {
    params,
    withCredentials: true
  });
}
  getTransactionById(id:number){
    return this.http.get(`${this.api_url}/transactions/${id}`,{withCredentials:true});
  }
editTransaction(id:number,trans:any){
return this.http.put(`${this.api_url}/transactions/${id}`,trans,{withCredentials:true});
}
deleteTransaction(id:number){
    return this.http.patch(`${this.api_url}/transactions/${id}`,id,{withCredentials:true});
  }
bulkUpload(file:any){
  const formData=new FormData();
  formData.append("file",file);
  // yeh file name "file" should be same as the request param in backend route
  return this.http.post(`${this.api_url}/transactions/bulk-upload`,formData, {
   
    withCredentials: true
  })
}
getDashboard(){
  return this.http.get(`${this.api_url}/getDashboard`,{withCredentials:true});
}
analyzeDashboard(dashboardData: any){
    return this.http.post<any>(`${this.api_url}/analyze`, dashboardData,{withCredentials:true});
  }
}
