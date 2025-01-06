import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Item } from '../models/item';
import { CookieService } from 'ngx-cookie-service';


@Injectable({
  providedIn: 'root'
})
export class ItemService {

  constructor(private http: HttpClient, private cookieService: CookieService) { }

  backendURL = "http://localhost:8080/items";

  getAvailableItem(): Observable<Item[]>{
    const token = this.cookieService.get("token");
      return this.http.get<Item[]>(this.backendURL+"/available", { headers: {
        "Authorization" : "Bearer "+token
        }
      }
    );
  }

  getItem(id?: number) : Observable<Item[] | Item>{
    const token = this.cookieService.get("token");
    if(id) {
      return this.http.get<Item>(this.backendURL+"/"+id, { headers: {
        "Authorization" : "Bearer "+token
        }
      }
    );
    } else {
      return this.http.get<Item[]>(this.backendURL, { headers: {
        "Authorization" : "Bearer "+token
        }
      }
    );
    }
  }

  getItemByUserId(id: number): Observable<Item[]> {
    const token = this.cookieService.get("token");
    return this.http.get<Item[]>(this.backendURL+"/user/"+id, { headers: {
      "Authorization" : "Bearer "+token
      }
    }
  );
  }

  saveItem(item: Item): Observable<Item> {
    const token = this.cookieService.get("token");
    if(item.id>0) {
      return this.http.put<Item>(this.backendURL+"/"+item.id, item, { headers: {
        "Authorization" : "Bearer "+token
        }
      }
    );
    } else {
      return this.http.post<Item>(this.backendURL, item, { headers: {
        "Authorization" : "Bearer "+token
        }
      }
    );
    }
  }

  deleteItem(id: number): Observable<any> {
    const token = this.cookieService.get("token");
    return this.http.delete(this.backendURL+"/"+id, { headers: {
      "Authorization" : "Bearer "+token
      }
    }
  );
  }
}
