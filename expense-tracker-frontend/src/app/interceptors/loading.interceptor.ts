import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { finalize, Observable } from 'rxjs';
import { AuthService } from '../auth/auth.service';

@Injectable()
export class LoadingInterceptor implements HttpInterceptor {
constructor(private loadingService: AuthService) {}
  intercept(request: HttpRequest<unknown>, next: HttpHandler) {
    this.loadingService.show(); // Show loader when request starts

    return next.handle(request).pipe(
      finalize(() => {
        this.loadingService.hide(); // Hide loader when request finishes (complete or error)
      })
    );
  }
}
