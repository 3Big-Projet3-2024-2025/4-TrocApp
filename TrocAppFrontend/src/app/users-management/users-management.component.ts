import { Component } from '@angular/core';
import {  OnInit } from '@angular/core';
import { User } from '../user';
import { UsersService } from '../users.service';
import { NgFor } from '@angular/common';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import{ Role } from '../role';
import { Router } from '@angular/router';



@Component({
  selector: 'app-users-management',
  standalone: true,
  imports: [NgFor, CommonModule, FormsModule],
  templateUrl: './users-management.component.html',
  styleUrl: './users-management.component.css'
})
export class UsersManagementComponent implements OnInit{
  users: User[] = []; // Tableau d'utilisateurs
  notificationVisible = false;
  editingUser: User | null = null;
  roles: Role[] = [];  

  constructor(private usersService: UsersService, private router: Router) {}

 

    ngOnInit(): void {
      this.fetchUsers();
      this.fetchRoles();
    }
  
      // Fonction pour formater l'adresse
    formatAddress(address: any): string {
    if (!address) return 'Adresse non disponible';
    return `${address.number} ${address.street}, ${address.city} ${address.zipCode}`;
  }

  fetchRoles() {
    this.usersService.getRoles().subscribe(
      (data) => this.roles = data,  // Affecte les rôles récupérés
      (error) => console.error('Error fetching roles:', error)
    );
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

    
  // Exemple dans users-management.component.ts
editUser(userId: number): void {
  this.router.navigate(['/edit/' + userId]);  // Vérifiez que cette syntaxe est correcte
}


}


