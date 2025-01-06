import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject, of } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap, map } from 'rxjs/operators';
import { User } from '../user';
import { Role } from '../role';
import { CookieService } from 'ngx-cookie-service';

interface PaginatedResponse {
  content: User[];  
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private apiUrl = 'http://localhost:8080/users';  // Base URL for the API
  private searchTerms = new Subject<string>();  // Subject to manage search terms

  constructor(private http: HttpClient, private cookieService : CookieService) {}

  // Fetch a list of users with pagination details
  getUsers(): Observable<PaginatedResponse> {
    const token = this.cookieService.get("token");
    return this.http.get<PaginatedResponse>(this.apiUrl, {headers: {Authorization: `Bearer ${token}`}});
   
  }

  // Fetch a user by ID
  getUserById(id: number): Observable<User> {
    const token = this.cookieService.get("token");
    return this.http.get<User>(`${this.apiUrl}/${id}`,{headers: {Authorization: `Bearer ${token}`}});
  }

  // Delete a user by ID
  deleteUser(id: number): Observable<void> {
    const token = this.cookieService.get("token");
    return this.http.delete<void>(`${this.apiUrl}/${id}`,{headers: {Authorization: `Bearer ${token}`}});
  }

  // Update a user's details
  updateUser(user: Partial<User>): Observable<User> {
    const token = this.cookieService.get("token");
    return this.http.put<User>(`http://localhost:8080/users/update-user`, user,{headers: {Authorization: `Bearer ${token}`}});
  }

  // Fetch the roles available for users
  getRoles(): Observable<Role[]> {
    const token = this.cookieService.get("token");
    return this.http.get<Role[]>(`${this.apiUrl}/roles`,{headers: {Authorization: `Bearer ${token}`}}); 
  }

  /* Update the roles of a user (commented out for now)
  updateUserRoles(userId: number, roleIds: number[]): Observable<void> {
    return this.http.put<void>(`http://localhost:8080/users/${userId}/roles`, roleIds);
  }*/

  // Update the roles of a user (new method)
  updateUserRoles(userId: number, roleIds: number[]): Observable<User> {
    const token = this.cookieService.get("token");
    return this.http.put<User>(`${this.apiUrl}/${userId}/roles`, roleIds,{headers: {Authorization: `Bearer ${token}`}});
  }

  // Fetch all roles available for users
  getAllRoles(): Observable<Role[]> {
    const token = this.cookieService.get("token");
    return this.http.get<Role[]>(`${this.apiUrl}/getAllRoles`,{headers: {Authorization: `Bearer ${token}`}});
  }

  // Fetch list of zip codes
  getZipCodes(): Observable<number[]> {
    const token = this.cookieService.get("token");
    return this.http.get<number[]>(`${this.apiUrl}/zipcodes`,{headers: {Authorization: `Bearer ${token}`}});  
  }

  // Fetch list of cities
  getCities(): Observable<string[]> {
    const token = this.cookieService.get("token");
    return this.http.get<string[]>(`${this.apiUrl}/cities`,{headers: {Authorization: `Bearer ${token}`}});   
  }

  // Fetch list of streets
  getStreets(): Observable<string[]> {
    const token = this.cookieService.get("token");
    return this.http.get<string[]>(`${this.apiUrl}/streets`,{headers: {Authorization: `Bearer ${token}`}});  
  }

  // Fetch list of numbers
  getNumbers(): Observable<string[]> {
    const token = this.cookieService.get("token");
    return this.http.get<string[]>(`${this.apiUrl}/numbers`,{headers: {Authorization: `Bearer ${token}`}});  
  }

  // Push a search term into the observable stream
  search(term: string): void {
   
    this.searchTerms.next(term);
  }

  // Return an observable of User arrays based on the search term
  getSearchResults(): Observable<User[]> {
  
    return this.searchTerms.pipe(
      debounceTime(300),  // Wait for 300ms pause in events
      distinctUntilChanged(),  // Ignore repeated search terms
      switchMap((term: string) => this.searchUsers(term))  // Switch to a new observable based on the search term
    );
  }

  // Make the actual HTTP request for search
  private searchUsers(term: string): Observable<User[]> {
    const token = this.cookieService.get("token");
    if (!term.trim()) {
      return of([]);  // Return an empty array if search term is empty
    }
    return this.http.get<User[]>(`${this.apiUrl}/search?query=${term}`,{headers: {Authorization: `Bearer ${token}`}});
  }

  // Fetch the items associated with a user
  getUserItems(userId: number): Observable<any[]> {
    const token = this.cookieService.get("token");
    return this.http.get<any[]>(`${this.apiUrl}/${userId}/items`,{headers: {Authorization: `Bearer ${token}`}});
  }

  // Toggle the block status of a user
  toggleBlockUser(userId: number): Observable<User> {
    const token = this.cookieService.get("token");
    return this.http.put<User>(`${this.apiUrl}/users/${userId}/block`, {},{headers: {Authorization: `Bearer ${token}`}});
  }

}
