import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Exchange } from '../models/exchange';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class ExchangeService {

  constructor(private http: HttpClient, private cookieService: CookieService) { }

  backendURL = "http://localhost:8080/exchanges";

  saveExchange(exchange: Exchange): Observable<Exchange> {
    const token = this.cookieService.get("token");
    if(exchange.id_exchange>0) {
      return this.http.put<Exchange>(this.backendURL+"/"+exchange.id_exchange, exchange, { headers: {
        "Authorization" : "Bearer "+token
        }
      }
    );
    } else {
      return this.http.post<Exchange>(this.backendURL, exchange, { headers: {
        "Authorization" : "Bearer "+token
        }
      }
    );
    }
  }

  getExchange(id?: number): Observable<Exchange[] | Exchange> {
    const token = this.cookieService.get("token");
    if(id) {
      return this.http.get<Exchange>(this.backendURL+"/"+id, { headers: {
        "Authorization" : "Bearer "+token
        }
      }
    );
    } else {
      return this.http.get<Exchange[]>(this.backendURL, { headers: {
        "Authorization" : "Bearer "+token
        }
      }
    );
    }
  }

  deleteExchange(id: number): Observable<any> {
    const token = this.cookieService.get("token");
    return this.http.delete<any>(this.backendURL+"/"+id, { headers: {
      "Authorization" : "Bearer "+token
      }
    }
  );
  }

  
  getExchangeByUserId(id: number): Observable<Exchange[]> {
    const token = this.cookieService.get("token");
    return this.http.get<Exchange[]>(this.backendURL+"/user/"+id, { headers: {
      "Authorization" : "Bearer "+token
      }
    }
  );
  }
}
