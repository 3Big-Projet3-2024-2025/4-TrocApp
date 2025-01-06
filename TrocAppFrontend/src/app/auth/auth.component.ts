import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router, RouterModule  } from '@angular/router';
import { FormsModule } from '@angular/forms';
import {CookieService} from "ngx-cookie-service";

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [RouterModule, FormsModule],
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.css'
})
export class AuthComponent {

  username: string = '';
  password: string = '';

  erreurMessage = "";

  constructor(private service: AuthService, private cookieService: CookieService, private router: Router) { }

  verifierLogin() {
    this.service.login(this.username, this.password).subscribe({
      next: (token) => {
        console.log(token);
        this.cookieService.set('token', token.accessToken);
        this.router.navigate(['/']);
      },
      error: (erreur) => {
        this.erreurMessage = erreur.error;
      },
      complete: () => { }
    });
  }
}
