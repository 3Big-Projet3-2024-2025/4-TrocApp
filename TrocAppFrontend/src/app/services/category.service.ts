import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Category } from '../models/category.model';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private apiUrl = 'http://localhost:8080/api/categories'; 

  constructor(private http: HttpClient) { }

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(this.apiUrl)
      .pipe(catchError(this.handleError));
  }

  getCategoryById(categoryId: number): Observable<Category> {
    const url = `${this.apiUrl}/${categoryId}`;
    return this.http.get<Category>(url)
      .pipe(catchError(this.handleError));
  }

  createCategory(category: Category): Observable<Category> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<Category>(this.apiUrl, category, { headers })
      .pipe(catchError(this.handleError));
  }

  modifyCategory(categoryId: number, updatedCategory: Category): Observable<Category> {
    const url = `${this.apiUrl}/${categoryId}`;
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.put<Category>(url, updatedCategory, { headers })
      .pipe(catchError(this.handleError));
  }

  deleteCategory(categoryId: number): Observable<any> {
        const url = `${this.apiUrl}/${categoryId}`;
        return this.http.delete(url, { responseType: 'text' })
          .pipe(catchError(this.handleError));
  }    

  private handleError(error: HttpErrorResponse): Observable<never> {
    if (error.status === 400) {
      // Handle the case when category already exists
      if (error.error.message && error.error.message.includes('already exists')) {
        return throwError('This category already exists. Please choose a different name.');
      }
      // Handle the case when category contains items and cannot be deleted
      if (error.error.message && error.error.message.includes('contains items')) {
        return throwError('This category cannot be deleted because it contains items.');
      }
    }

    // General error handling
    console.error('An error occurred:', error);
    return throwError('Something went wrong; please try again later.');
  }
}