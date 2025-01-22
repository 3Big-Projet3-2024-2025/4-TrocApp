import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { User } from '../models/user';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-registration-form',
  standalone: true,
  imports: [RouterModule, FormsModule],
  templateUrl: './registration-form.component.html',
  styleUrl: './registration-form.component.css'
})
export class RegistrationFormComponent {

  userToAdd: User = {
    id: -1,
    address:
    {
      id: 0,
      street: '',
      number: '',
      city: '',
      zipCode: 0,
      latitude: 0,
      longitude: 0
    },
    email: '',
    firstName: '',
    lastName: '',
    username: '',
    password: '',
    rating: -1, 
    roles: [],
    rolesInput: '',
    actif: true
  }

  errorMessage = "";

  constructor (private service: AuthService, private router: Router) { }

  saveUser()
  {
    console.log(this.userToAdd);
    const subscribe = this.service.saveUser(this.userToAdd).subscribe({
      next: (user) => {
        this.router.navigate(['/']);
        subscribe.unsubscribe();
      },

      error: (error) => {
        this.errorMessage = error;
        subscribe.unsubscribe();
      }
      
    })
  }

}
