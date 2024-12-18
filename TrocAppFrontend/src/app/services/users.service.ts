import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
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

  updateUserRoles(userId: number, roleIds: number[]): Observable<void> {
    return this.http.put<void>(`http://localhost:8080/users/${userId}/roles`, roleIds);
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


}
