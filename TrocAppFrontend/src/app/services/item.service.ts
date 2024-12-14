import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Item } from '../item';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  constructor(private http: HttpClient) { }

  backendURL = "http://localhost:8080/items";

  getAvailableItem(): Observable<Item[]>{
      return this.http.get<Item[]>(this.backendURL+"/available");
  }

  getItem(id?: number) : Observable<Item[] | Item>{
    if(id) {
      return this.http.get<Item>(this.backendURL+"/"+id);
    } else {
      return this.http.get<Item[]>(this.backendURL);
    }
  }

  saveItem(item: Item): Observable<Item> {
    if(item.id>0) {
      return this.http.put<Item>(this.backendURL+"/"+item.id, item);
    } else {
      return this.http.post<Item>(this.backendURL, item);
    }
  }

  updateItem(item: Item) : Observable<Item> {
    return this.http.put<Item>(this.backendURL+"/"+item.id, item);
  }

  deleteItem(id: number): Observable<any> {
    return this.http.delete(this.backendURL+"/"+id);
  }
}
