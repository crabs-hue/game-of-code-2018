import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/do';
import { environment } from '../environments/environment';

@Injectable()
export class AppHttpInterceptor implements HttpInterceptor {

  private static ASSET_FOLDERS = ['/assets'];

  constructor() {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Clone the request to add the new header.
    let apiPrefix = '';
    if (AppHttpInterceptor.ASSET_FOLDERS.find(f => req.url.indexOf(f) !== 0)) {
        apiPrefix = environment.backendUrl + 'api';
    }
    const authReq = req.clone(
      {
        url: apiPrefix + req.url
      }
    );
    // send the newly created request
    return next.handle(authReq);
  }
}
