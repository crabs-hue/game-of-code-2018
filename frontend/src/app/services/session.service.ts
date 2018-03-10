import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class SessionService {

  constructor(private http: HttpClient) { }

  getUser(): Observable<{value: string}> {
    return this.http.get<{value: string}>('/user');
  }

}
