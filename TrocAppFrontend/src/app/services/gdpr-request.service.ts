import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { GdprRequest } from '../models/gdpr-request.modele';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class GdprRequestService {
  private baseUrl = 'http://localhost:8080/api/gdpr';  // API URL

  constructor(private http: HttpClient) { }

  /*createRequest(request: GdprRequest): Observable<GdprRequest> {
    return this.http.post<GdprRequest>(this.baseUrl, request);
  }
    createRequest(request: GdprRequest): Observable<GdprRequest> {
      console.log('Sending request:', request);
      return this.http.post<GdprRequest>(this.baseUrl, request).pipe(
        tap(
          response => console.log('Success response:', response),
          error => console.log('Error response:', error)
        )
      );
    }*/
      createRequest(request: GdprRequest): Observable<GdprRequest> {
        const headers = new HttpHeaders({
          'Content-Type': 'application/json'
        });
      
        // Filter and prepare request data
        const filteredRequest = {
          ...request,
          user: {
            id: request.user.id,
            firstName: request.user.firstName,
            lastName: request.user.lastName,
            email: request.user.email,
            active: request.user.actif  // Convert French 'actif' to English 'active'
          }
        };
      
        console.log('Filtered request to send:', filteredRequest);
        
        return this.http.post<GdprRequest>(this.baseUrl, filteredRequest, { headers }).pipe(
          tap(
            response => console.log('Success response:', response),
            error => {
              console.log('Error details:', {
                status: error.status,
                message: error.error?.message,
                details: error.error
              });
            }
          )
        );
      }

  // Get pending requests
  /*getPendingRequests(): Observable<GdprRequest[]> {
    return this.http.get<GdprRequest[]>(`${this.baseUrl}/pending`);
  }*/
    getPendingRequests(): Observable<GdprRequest[]> {
      return this.http.get<GdprRequest[]>(`${this.baseUrl}/pending`).pipe(
        tap(requests => console.log('Pending requests:', requests))
      );
    }
  // Get processed requests
  getTreatedRequests(): Observable<GdprRequest[]> {
    return this.http.get<GdprRequest[]>(`${this.baseUrl}/processed`);
  }

  // Deactivate a user
  deactivateUser(userId: number): Observable<string> {
    return this.http.put<string>(`${this.baseUrl}/${userId}/deactivate`, {});
  }

  // Add new method to update request status
  processRequest(requestId: number, response: string): Observable<GdprRequest> {
    return this.http.put<GdprRequest>(`${this.baseUrl}/${requestId}/processed`, response).pipe(
      tap(
        response => console.log('Request processed successfully:', response),
        error => console.log('Error processing request:', error)
      )
    );
  }
  
  updateRequestStatus(requestId: number, status: string, comment: string): Observable<GdprRequest> {
  const updatePayload = {
    status: status,
    comment: comment
  };
  return this.http.put<GdprRequest>(`${this.baseUrl}/${requestId}/status`, updatePayload).pipe(
    tap(
      response => console.log('Request status updated successfully:', response),
      error => console.log('Error updating request status:', error)
    )
  );
  }

}
