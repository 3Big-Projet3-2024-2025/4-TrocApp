import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject, of } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap, map } from 'rxjs/operators';
import { User } from '../user';
import { Role } from '../role';

interface PaginatedResponse {
  content: User[];  // Tableau d'utilisateurs
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private apiUrl = 'http://localhost:8080/users';
  private searchTerms = new Subject<string>();


  constructor(private http: HttpClient) {}

  getUsers(): Observable<PaginatedResponse> {
    return this.http.get<PaginatedResponse>(this.apiUrl);
  }

  getUserById(id: number): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/${id}`);
  }

  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  updateUser(user: Partial<User>): Observable<User> {
    return this.http.put<User>(`http://localhost:8080/users/update-user`, user);
  }

  getRoles(): Observable<Role[]> {
    return this.http.get<Role[]>(`${this.apiUrl}/roles`);  // Retourne la liste des rôles
  }

 /* updateUserRoles(userId: number, roleIds: number[]): Observable<void> {
    return this.http.put<void>(`http://localhost:8080/users/${userId}/roles`, roleIds);
  }*/

  // Nouvelle méthode pour mettre à jour les rôles d'un utilisateur
  updateUserRoles(userId: number, roleIds: number[]): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/${userId}/roles`, roleIds);
  }

  getAllRoles(): Observable<Role[]> {
    return this.http.get<Role[]>(`${this.apiUrl}/getAllRoles`);
  }

  getZipCodes(): Observable<number[]> {
    return this.http.get<number[]>(`${this.apiUrl}/zipcodes`);  // Effectue une requête GET pour récupérer les zip codes (ici des nombres)
  }

  getCities(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/cities`);  // Effectue une requête GET pour récupérer les zip codes (ici des nombres)
  }
  getStreets(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/streets`);  // Effectue une requête GET pour récupérer les zip codes (ici des nombres)
  }
  getNumbers(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/numbers`);  // Effectue une requête GET pour récupérer les zip codes (ici des nombres)
  }




   // Push a search term into the observable stream
   search(term: string): void {
    this.searchTerms.next(term);
  }

  // Return an observable of User arrays based on the search term
  getSearchResults(): Observable<User[]> {
    return this.searchTerms.pipe(
      debounceTime(300), // Wait for 300ms pause in events
      distinctUntilChanged(), // Ignore if next search term is same as previous
      switchMap((term: string) => this.searchUsers(term))
    );
  }

  // Make the actual HTTP request
  private searchUsers(term: string): Observable<User[]> {
    if (!term.trim()) {
      return of([]); // Return empty array if search term is empty
    }
    return this.http.get<User[]>(`${this.apiUrl}/search?query=${term}`);
  }




  // Dans users.service.ts
getUserItems(userId: number): Observable<any[]> {
  return this.http.get<any[]>(`${this.apiUrl}/${userId}/items`);
}


}