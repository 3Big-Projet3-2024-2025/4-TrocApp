import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { User } from '../user';
import { UsersService } from '../services/users.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NgFor, NgIf} from '@angular/common';
import { Role } from '../role';
import { Observable } from 'rxjs';
import { AsyncPipe } from '@angular/common';


@Component({
  selector: 'app-user-edit',
  standalone: true,
  imports: [FormsModule,NgIf,AsyncPipe],
  templateUrl: './user-edit.component.html',
  styleUrl: './user-edit.component.css'
})
export class UserEditComponent {
  //editingUser2: User | null = null;
  roleList! : Observable<Role[]>;
  listeRoleSelectionnes! : number[];
  user!: User;
  message: string | null = null;
  success: boolean = false;
  editingUser: User = { 
    id: 0,
    firstName: '',
    lastName: '',
    email: '',
    address: {         
      zipCode: '',
      city: '',
      street: '',
      number: ''
    },
    rating: 0,
    addressId: 0,
    roles: [],
    rolesInput: '',
    blocked: false
  };

  zipCodes: number[] = [];  
  streets: string[] = [];
  numbers: string[] = [];
  cities: string[] = [];


  // Dictionnaire de rôles par ID utilisateur
  roles: { [userId: number]: Role[] } = {};

  availableRoles: Role[] = [];

  constructor(
    private usersService: UsersService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
       // Fetch all available roles
    this.usersService.getAllRoles().subscribe(roles => {
      // Stock the roles in the dictionary
      this.roles[this.editingUser.id] = roles;
    });

    

    this.route.params.subscribe(params => {
      if (params["id"])
      {
        const subscribe = this.usersService.getUserById(params["id"]).subscribe(user => {
          this.editingUser = user as User;
          subscribe.unsubscribe();
        })
      }
    });

    this.roleList = this.usersService.getRoles() as Observable<Role[]>;
  }


  

 
  isRoleSelected(role: Role): boolean {
    //  Verify if the role is already assigned to the user
    return this.editingUser.roles.some(r => r.id === role.id);
  }

  toggleRole(role: Role): void {
    const index = this.editingUser.roles.findIndex(r => r.id === role.id);

    if (index !== -1) {
        // Present role, remove it
        this.editingUser.roles.splice(index, 1);
    } else {
        // Absent role, add it
        this.editingUser.roles.push(role);
    }

    // To update the list of roles in the database
    const updatedRoleIds = this.editingUser.roles.map(r => r.id);

    this.usersService.updateUserRoles(this.editingUser.id, updatedRoleIds).subscribe({
        next: () => console.log(`Roles successfully updated for user ${this.editingUser.id}`),
        error: (err) => console.error('Error updating roles:', err)
    });
}

  submitEditForm(): void {

      this.usersService.updateUser(this.editingUser).subscribe({
        next: (user) => {
          this.message = 'User successfully updated.';
          this.success = true;
          setTimeout(() => this.router.navigate(['/users-management']), 2000);  // Retourne à la liste après 2 secondes
        },
        error: (error) => {
          this.message = 'Error during user update.';
          this.success = false;
        }
      });
  }

  goBack(): void {
    this.router.navigate(['/users-management']);  // Return to the user list page
  }
}
