import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  private baseUrl = 'http://localhost:8080/addresses';

  constructor(private httpClient : HttpClient, private cookieService: CookieService) { }

  private getAuthHeaders(): HttpHeaders {
    const token = this.cookieService.get('token');
    return new HttpHeaders({
      Authorization: 'Bearer ' + token
    });
  }
    getAddressById(id: number): Observable<any> {
      const headers = this.getAuthHeaders();
      return this.httpClient.get(`${this.baseUrl}/${id}`, { headers } ).pipe(
        catchError((error) => {
          console.error("Error fetching address with ID ${id}:", error);
          return throwError(() => new Error("Error fetching address with ID `${id}`."));
        })
      );
    }

}