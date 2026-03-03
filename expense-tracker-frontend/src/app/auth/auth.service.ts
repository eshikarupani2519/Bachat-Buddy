import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }
   private _loading = new BehaviorSubject<boolean>(false);
    public readonly loading$: Observable<boolean> = this._loading.asObservable();
   
  private api_url='http://localhost:8080'
  register(body: any) {
    return this.http.post(`${this.api_url}/register`, body);
  }
  login(body:any){
    const bod=new HttpParams().set("email",body.email).set("password",body.password);

    return this.http.post(`${this.api_url}/login`,bod.toString(),
  {headers:new HttpHeaders().set('Content-type','application/x-www-form-urlencoded'),withCredentials:true});
  }
  forgotPassword(email:any){
    return this.http.post(`${this.api_url}/reset`,email);
  }
  resetPassword(body:any,token:any){
    return this.http.patch(`${this.api_url}/changePassword/${token}`,body);
  }
  validateToken(token:string){
    return this.http.post(`${this.api_url}/validateToken/${token}`,token);
  }
  // loaderssss
  show(): void {
    
      this._loading.next(true);
    
  }

  hide(): void {
   
      this._loading.next(false);
    
  }
}
