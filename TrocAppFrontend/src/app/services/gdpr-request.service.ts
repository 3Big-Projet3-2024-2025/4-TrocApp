import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { GdprRequest } from '../models/gdpr-request.model';

@Injectable({
  providedIn: 'root'
})
export class GdprRequestService {

  private apiUrl = 'http://localhost:8080/api/gdpr'; // URL backend

  constructor(private http: HttpClient) { }

  // Envoie la requête GDPR au backend
  submitGdprRequest(request: GdprRequest): Observable<any> {
    return this.http.post(this.apiUrl, request);
  }
}
