import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { GdprRequest } from '../models/gdpr-request.modele';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class GdprRequestService {
  private apiUrl = 'http://localhost:8080/api/gdpr';

  constructor(private http: HttpClient, private cookieService: CookieService) {}

  private getAuthHeaders(): HttpHeaders {
    const token = this.cookieService.get('token');
    return new HttpHeaders({
      Authorization: 'Bearer ' + token
    });
  }

  getPendingRequests(): Observable<GdprRequest[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<GdprRequest[]>(`${this.apiUrl}/pending`, { headers })
      .pipe(catchError(this.handleError));
  }

  getProcessedRequests(): Observable<GdprRequest[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<GdprRequest[]>(`${this.apiUrl}/processed`, { headers })
      .pipe(catchError(this.handleError));
  }

  processRequest(requestId: number, response: string): Observable<GdprRequest> {
    const headers = this.getAuthHeaders();
    return this.http.put<GdprRequest>(`${this.apiUrl}/${requestId}/processed`, response, { headers })
      .pipe(catchError(this.handleError));
  }

  deactivateUser(userId: number): Observable<string> {
    const headers = this.getAuthHeaders();
    return this.http.put<string>(`${this.apiUrl}/${userId}/deactivate`, {}, { headers })
      .pipe(catchError(this.handleError));
  }

  createRequest(request: GdprRequest): Observable<GdprRequest> {
    const headers = this.getAuthHeaders();
    return this.http.post<GdprRequest>(this.apiUrl, request, { headers })
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('An error occurred:', error);
    return throwError('Something went wrong; please try again later.');
  }
}
