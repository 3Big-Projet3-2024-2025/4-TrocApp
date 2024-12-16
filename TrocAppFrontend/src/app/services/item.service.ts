import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class ItemService {
  private baseUrl = 'http://localhost:8080/items';

  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };

  constructor(private httpClient : HttpClient) { }

  getItems(): Observable<any> {
    return this.httpClient.get(`${this.baseUrl}`).pipe(
      catchError((error) => {
        console.error('Error fetching items:', error);
        return throwError(() => new Error('Error fetching items.'));
      })
    );
  }

  getAvailableItemsTrue(): Observable<any> {
    return this.httpClient.get(`${this.baseUrl}?available=true`).pipe(
      catchError((error) => {
        console.error('Error fetching available items:', error);
        return throwError(() => new Error('Error fetching available items.'));
      })
    );
  }

  getItemById(id: number): Observable<any> {
    return this.httpClient.get(`${this.baseUrl}/${id}`).pipe(
      catchError((error) => {
        console.error(`Error fetching item with ID ${id}:`, error);
        return throwError(() => new Error(`Error fetching item with ID ${id}.`));
      })
    );
  }

  proposeExchange(item: any): Observable<any> {
    return this.httpClient.post(`${this.baseUrl}/exchange`, item, this.httpOptions).pipe(
      catchError((error) => {
        console.error('Error proposing exchange:', error);
        return throwError(() => new Error('Error proposing exchange.'));
      })
    );
  }
}