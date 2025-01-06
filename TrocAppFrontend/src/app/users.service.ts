import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from './user';
import { Role } from './role';
import { CookieService } from 'ngx-cookie-service';

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

  constructor(private http: HttpClient, private cookieService: CookieService) {}

  getUsers(): Observable<PaginatedResponse> {
    const token = this.cookieService.get("token");

    return this.http.get<PaginatedResponse>(this.apiUrl, {
      headers: {
        'Authorization': 'Bearer ' + token
      }
    });
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

}
