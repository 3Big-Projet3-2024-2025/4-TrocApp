import { Component } from '@angular/core';

@Component({
  selector: 'app-users-management',
  standalone: true,
  imports: [],
  templateUrl: './users-management.component.html',
  styleUrl: './users-management.component.css'
})
export class UsersManagementComponent implements OnInit{
  users: User[] = []; // Tableau d'utilisateurs
  notificationVisible = false;

  constructor(private usersService: UsersService) {}

  /*ngOnInit(): void {
    this.usersService.getUsers().subscribe(
      (data) => {
        console.log('API Response:', data);  // Vérifiez ce qui est retourné par l'API
        this.users = data.content;  // Assurez-vous que vous affectez le tableau `content` à `users`
      },
      (error) => {
        console.error('Error fetching users:', error);
      }
    );
  }*/

    ngOnInit(): void {
      this.fetchUsers();
    }
  
    // Récupérer la liste des utilisateurs
    fetchUsers() {
      this.usersService.getUsers().subscribe(
        (data) => this.users = data.content,
        (error) => console.error('Error fetching users:', error)
      );
    }
  
    // Confirmation double pour suppression
    confirmDelete(userId: number): void {
      if (confirm("Êtes-vous sûr de vouloir supprimer cet utilisateur ?")) {
        if (confirm("Vraiment sûr ? Cette action est irréversible.")) {
          this.deleteUser(userId);
        }
      }
    }
  
    // Suppression de l'utilisateur
    deleteUser(userId: number): void {
      this.usersService.deleteUser(userId).subscribe(
        () => {
          this.notificationVisible = true; // Affiche la notification
          setTimeout(() => this.notificationVisible = false, 3000); // Cache après 3 secondes
          this.fetchUsers(); // Rafraîchit la liste
        },
        (error) => console.error('Error deleting user:', error)
      );
    }
}

