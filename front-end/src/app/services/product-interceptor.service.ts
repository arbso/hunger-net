import { TokenService } from './token.service';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HTTP_INTERCEPTORS } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProductIntrceptorService /*implements HttpInterceptor*/ {

  // constructor(private tokenService: TokenService) { }

  // intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
  //   let autReq = req;
  //   const token = this.tokenService.getToken();
  //   if (token != null) {
  //     autReq = req.clone({ headers: req.headers.set('Authorization', 'Bearer ' + token) });
  //   }
  //   return next.handle(autReq);
  // }

}
// export const interceptorProvider = [{provide: HTTP_INTERCEPTORS, useClass: ProductInterceptorService, multi: true}];

