import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { User } from '../user';
import { UsersService } from '../services/users.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NgFor, NgIf } from '@angular/common';
import { Role } from '../role';
import { Observable } from 'rxjs';
import { AsyncPipe } from '@angular/common';

@Component({
  selector: 'app-user-edit',
  standalone: true,
  imports: [FormsModule, NgIf, AsyncPipe],
  templateUrl: './user-edit.component.html',
  styleUrl: './user-edit.component.css'
})
export class UserEditComponent {
  // Observable to hold the list of roles
  roleList!: Observable<Role[]>;

  // Array to hold selected role IDs
  listeRoleSelectionnes!: number[];

  // The user being edited
  user!: User;

  // Message and status for success/error
  message: string | null = null;
  success: boolean = false;

  // Default empty user object for editing
  editingUser: User = { 
    id: 0,
    firstName: '',
    lastName: '',
    email: '',
    address: {         
      zipCode: '',
      city: '',
      street: '',
      number: '',
      longitude: 0,
      latitude: 0
    },
    rating: 0,
    addressId: 0,
    roles: [],
    rolesInput: '',
    blocked: false
  };

  // Arrays to hold address-related data
  zipCodes: number[] = [];  
  streets: string[] = [];
  numbers: string[] = [];
  cities: string[] = [];

  // Dictionary to store roles by user ID
  roles: { [userId: number]: Role[] } = {};

  // Available roles to assign to the user
  availableRoles: Role[] = [];

  constructor(
    private usersService: UsersService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Fetch all available roles when component is initialized
    this.usersService.getAllRoles().subscribe(roles => {
      // Store the roles in the dictionary, using user ID as the key
      this.roles[this.editingUser.id] = roles;
    });

    // Fetch user details based on the route parameter (user ID)
    this.route.params.subscribe(params => {
      if (params["id"]) {
        const subscribe = this.usersService.getUserById(params["id"]).subscribe(user => {
          this.editingUser = user as User;  // Set the editing user
          subscribe.unsubscribe();  // Unsubscribe after fetching the user
        });
      }
    });

    // Fetch the list of available roles from the API
    this.roleList = this.usersService.getRoles() as Observable<Role[]>;
  }

  // Check if a role is selected for the user
  isRoleSelected(role: Role): boolean {
    return this.editingUser.roles.some(r => r.id === role.id);
  }

  // Toggle the assignment of a role for the user
  toggleRole(role: Role): void {
    const index = this.editingUser.roles.findIndex(r => r.id === role.id);

    if (index !== -1) {
      // If role is already assigned, remove it
      this.editingUser.roles.splice(index, 1);
    } else {
      // If role is not assigned, add it
      this.editingUser.roles.push(role);
    }

    // Update the user's roles in the database
    const updatedRoleIds = this.editingUser.roles.map(r => r.id);
    this.usersService.updateUserRoles(this.editingUser.id, updatedRoleIds).subscribe({
      next: () => console.log(`Roles successfully updated for user ${this.editingUser.id}`),
      error: (err) => console.error('Error updating roles:', err)
    });
  }

  // Submit the edit form to update the user
  submitEditForm(): void {
    this.usersService.updateUser(this.editingUser).subscribe({
      next: (user) => {
        // On success, display a success message and navigate to the users list
        this.message = 'User successfully updated.';
        this.success = true;
        setTimeout(() => this.router.navigate(['/users-management']), 2000);  // Navigate back after 2 seconds
      },
      error: (error) => {
        // On error, display an error message
        this.message = 'Error during user update.';
        this.success = false;
      }
    });
  }

  // Navigate back to the users list page
  goBack(): void {
    this.router.navigate(['/users-management']);  // Navigate to user management page
  }
}
