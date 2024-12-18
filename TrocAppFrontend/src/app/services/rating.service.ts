import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Rating } from '../rating';

@Injectable({
  providedIn: 'root'
})
export class RatingService {

  private apiUrl = 'http://localhost:8080/ratings';

  constructor(private http: HttpClient) {}

  getReceivedRatings(userId: number): Observable<Rating[]> {
    return this.http.get<Rating[]>(`${this.apiUrl}/received/${userId}`);
  }

  addRating(rating: Rating): Observable<Rating> {
    return this.http.post<Rating>(`${this.apiUrl}/add`, rating);
  }

  updateRating(ratingId: number, updatedRating: Rating): Observable<Rating> {
    return this.http.put<Rating>(`${this.apiUrl}/${ratingId}`, updatedRating);
  }

  deleteRating(ratingId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${ratingId}`);
  }
}
