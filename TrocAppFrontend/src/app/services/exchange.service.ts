import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Exchange } from '../models/exchange';

@Injectable({
  providedIn: 'root'
})
export class ExchangeService {

  constructor(private http: HttpClient) { }

  backendURL = "http://localhost:8080/exchanges";

  saveExchange(exchange: Exchange): Observable<Exchange> {
    if(exchange.id_exchange>0) {
      return this.http.put<Exchange>(this.backendURL+"/"+exchange.id_exchange, exchange);
    } else {
      return this.http.post<Exchange>(this.backendURL, exchange);
    }
  }

  getExchange(id?: number): Observable<Exchange[] | Exchange> {
    if(id) {
      return this.http.get<Exchange>(this.backendURL+"/"+id);
    } else {
      return this.http.get<Exchange[]>(this.backendURL);
    }
  }

  deleteExchange(id: number): Observable<any> {
    return this.http.delete<any>(this.backendURL+"/"+id);
  }

  
  getExchangeByUserId(id: number): Observable<Exchange[]> {
    return this.http.get<Exchange[]>(this.backendURL+"/user/"+id);
  }
}
