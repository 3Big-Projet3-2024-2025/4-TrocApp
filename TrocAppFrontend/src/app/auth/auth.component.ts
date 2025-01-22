import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { CookieService } from 'ngx-cookie-service';

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

  login() {
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
