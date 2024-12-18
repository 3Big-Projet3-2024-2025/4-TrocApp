import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
//import { User } from '../user';
//import { UsersService } from '../users.service';
import { User } from '../models/user';
import { UsersService } from '../services/users.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NgIf, NgClass} from '@angular/common';

@Component({
  selector: 'app-user-edit',
  standalone: true,
  imports: [FormsModule,NgIf,NgClass],
  templateUrl: './user-edit.component.html',
  styleUrl: './user-edit.component.css'
})
export class UserEditComponent {
  //editingUser: User | null = null;
  message: string | null = null;
  success: boolean = false;
  editingUser: User = {  // Initialisation avec des valeurs par défaut
    id: 0,
    firstName: '',
    lastName: '',
    email: '',
    address: '',
    rating: 0,
    addressId: 0,
    roles: [],
    rolesInput: ''
  };

  constructor(
    private usersService: UsersService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      if (params["id"])
      {
        const subscribe = this.usersService.getUserById(params["id"]).subscribe(user => {
          this.editingUser = user as User;
          subscribe.unsubscribe();
        })
      }
    })
  }

  submitEditForm(): void {

      this.usersService.updateUser(this.editingUser).subscribe({
        next: (user) => {
          this.message = 'Utilisateur mis à jour avec succès.';
          this.success = true;
          setTimeout(() => this.router.navigate(['/']), 2000);  // Retourne à la liste après 2 secondes
        },
        error: (error) => {
          this.message = 'Erreur lors de la mise à jour de l\'utilisateur.';
          this.success = false;
        }
      });
  }

  goBack(): void {
    this.router.navigate(['/users-management']);  // Retourne à la page de liste des utilisateurs
  }
}
