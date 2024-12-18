import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { User } from '../user';
import { UsersService } from '../users.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NgFor, NgIf} from '@angular/common';
import { Role } from '../role';
import { Observable } from 'rxjs';
import { AsyncPipe } from '@angular/common';


@Component({
  selector: 'app-user-edit',
  standalone: true,
  imports: [FormsModule,NgFor,NgIf,AsyncPipe],
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
  editingUser: User = {  // Initialisation avec des valeurs par défaut
    id: 0,
    firstName: '',
    lastName: '',
    email: '',
    address: {          // L'initialisation de address doit être un objet Address
      zipCode: '',
      city: '',
      street: '',
      number: ''
    },
    rating: 0,
    addressId: 0,
    roles: [],
    rolesInput: ''
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
       // Charger tous les rôles disponibles
    this.usersService.getAllRoles().subscribe(roles => {
      // Stocker les rôles dans le dictionnaire
      this.roles[this.editingUser.id] = roles;
    });

    this.usersService.getZipCodes().subscribe(zipCodes => {
      this.zipCodes = zipCodes;  // Récupère la liste des codes postaux depuis l'API
    });

    this.usersService.getNumbers().subscribe(numbers => {
      this.numbers = numbers;  // Récupère la liste des codes postaux depuis l'API
    });

    this.usersService.getStreets().subscribe(streets => {
      this.streets = streets;  // Récupère la liste des codes postaux depuis l'API
    });

    this.usersService.getCities().subscribe(cities => {
      this.cities = cities;  // Récupère la liste des codes postaux depuis l'API
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
    // Vérifier si le rôle est déjà attribué à l'utilisateur
    return this.editingUser.roles.some(r => r.id === role.id);
  }

  toggleRole(role: Role): void {
    const index = this.editingUser.roles.findIndex(r => r.id === role.id);

    if (index !== -1) {
        // Rôle présent, le retirer
        this.editingUser.roles.splice(index, 1);
    } else {
        // Rôle absent, l'ajouter
        this.editingUser.roles.push(role);
    }

    // Mettre à jour la liste des rôles dans la base de données
    const updatedRoleIds = this.editingUser.roles.map(r => r.id);

    this.usersService.updateUserRoles(this.editingUser.id, updatedRoleIds).subscribe({
        next: () => console.log(`Roles successfully updated for user ${this.editingUser.id}`),
        error: (err) => console.error('Error updating roles:', err)
    });
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
