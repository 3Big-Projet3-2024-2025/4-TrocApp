import { Component } from '@angular/core';
import { RouterOutlet, RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { User } from './user';
import { UsersService } from './services/users.service';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { NgFor,CommonModule} from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from './auth.service';



@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterModule, HttpClientModule,NgFor, CommonModule, FormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'TrocAppFrontend';
  searchTerm: string = '';
  searchResults$: Observable<User[]>;

  constructor(
    private userService: UsersService,
    private router: Router,
    private authService: AuthService
  ) {
    this.searchResults$ = this.userService.getSearchResults();
  }

  isAdmin = false;

  ngOnInit()
  {
    this.isAdmin = this.authService.isAdmin();
  }

  onSearch(event: any) {
    this.userService.search(this.searchTerm);
  }

  onUserSelect(user: User) {
    this.searchTerm = ''; // Clear search
    this.router.navigate(['/user-profile', user.id]); // Navigate to user profile
  }
}
