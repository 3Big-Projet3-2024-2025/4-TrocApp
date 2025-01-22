import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';
import { jwtDecode } from 'jwt-decode';
import { User } from './models/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  roles: string[] = [];

  constructor(private http: HttpClient, private cookieService: CookieService)
  {
    this.initializeRoles();
  }

  initializeRoles(): void
  {
    const tokenDecoded = this.decodeToken();

    if (tokenDecoded && tokenDecoded.roles)
    {
      this.roles = tokenDecoded.roles.map((role: { authority: string }) => role.authority);
    }
  }

  login(username: string, password: string): Observable<any> {

    const body = new HttpParams()
      .set('username', username)
      .set('password', password);

    const headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded');

    return this.http.post("http://localhost:8080/auth/login", body, { headers });
  }

  saveUser(utilisateur: User): Observable<User>
  {
    const token = this.cookieService.get("token");

    return this.http.post<User>("http://localhost:8080/auth/create_account", utilisateur, {
      headers : {
        "Authorization" : "Bearer "+ token
      }
    });
  }

  getToken(): string
  {
    return this.cookieService.get('token');
  }

  decodeToken(): any
  {
    const token = this.getToken();
    let tokenDecoded;

    if (token)
    {
      try {

        tokenDecoded = jwtDecode(token);
        return tokenDecoded;

      } catch (erreur) {

        console.error('Token decoding failed', erreur);
        return null;

      }
    }

    return null;
  }

  getIDUserConnected(): number | null
  {
    const tokenDecoded = this.decodeToken();

    if (tokenDecoded && tokenDecoded.id)
    {
      return tokenDecoded.id;
    }

    return null;
  }

  getNameUserConnected(): string | null
  {
    const tokenDecoded = this.decodeToken();

    if (tokenDecoded && tokenDecoded.unique_name)
    {
      return tokenDecoded.unique_name;
    }

    return null;
  }

  getMailUserConnected(): string | null
  {
    const tokenDecoded = this.decodeToken();

    if (tokenDecoded && tokenDecoded.email)
    {
      return tokenDecoded.email;
    }

    return null;
  }

  isUser(): boolean
  {
    if (!this.roles)
      {
        return false;
      }
  
      return this.roles.includes('ROLE_admin');
  }

  isAdmin(): boolean
  {
    if (!this.roles)
    {
      return false;
    }

    return this.roles.includes('ROLE_admin');
  }
}
