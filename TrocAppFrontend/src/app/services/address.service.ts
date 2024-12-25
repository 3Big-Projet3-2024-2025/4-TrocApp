import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  private baseUrl = 'http://localhost:8080/addresses';

  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };

  constructor(private httpClient : HttpClient) { }

    getAddressById(id: number): Observable<any> {
      return this.httpClient.get(`${this.baseUrl}/${id}`).pipe(
        catchError((error) => {
          console.error(`Error fetching address with ID ${id}:`, error);
          return throwError(() => new Error(`Error fetching address with ID ${id}.`));
        })
      );
    }

}
