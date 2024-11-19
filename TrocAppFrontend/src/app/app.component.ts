import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { UsersManagementComponent } from './users-management/users-management.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, UsersManagementComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'TrocAppFrontend';
}
