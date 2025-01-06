import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Rating } from '../rating';
import { BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class RatingService {

  private apiUrl = 'http://localhost:8080/ratings';  // Base URL for ratings API
  private averageRatingSubject = new BehaviorSubject<number>(0);  // Subject to manage the average rating
  averageRating$ = this.averageRatingSubject.asObservable();  // Observable for components to subscribe to the average rating

  constructor(private http: HttpClient,private cookieService : CookieService) {}

  // Fetch ratings received by a user based on their userId
  getReceivedRatings(userId: number): Observable<Rating[]> {
    const token = this.cookieService.get("token");
    return this.http.get<Rating[]>(`${this.apiUrl}/received/${userId}`, {headers: {Authorization: `Bearer ${token}`}});
  }

  // Add a new rating
  addRating(rating: Rating): Observable<Rating> {
    const token = this.cookieService.get("token");
    return this.http.post<Rating>(`${this.apiUrl}/add`, rating, {headers: {Authorization: `Bearer ${token}`}});
  }

  // Update an existing rating based on its ratingId
  updateRating(ratingId: number, updatedRating: Rating): Observable<Rating> {
    const token = this.cookieService.get("token");
    return this.http.put<Rating>(`${this.apiUrl}/${ratingId}`, updatedRating, {headers: {Authorization: `Bearer ${token}`}});
  }

  // Delete a rating based on its ratingId
  deleteRating(ratingId: number): Observable<void> {
    const token = this.cookieService.get("token");
    return this.http.delete<void>(`${this.apiUrl}/${ratingId}`, {headers: {Authorization: `Bearer ${token}`}});
  }

  // Fetch the average rating of a user and update the BehaviorSubject
  getAverageRating(userId: number): Observable<number> {
    const token = this.cookieService.get("token");
    return this.http.get<number>(`${this.apiUrl}/average/${userId}`, {headers: {Authorization: `Bearer ${token}`}}).pipe(
      tap(average => this.averageRatingSubject.next(average))  // Update the BehaviorSubject with the new average rating
    );
  }
}
