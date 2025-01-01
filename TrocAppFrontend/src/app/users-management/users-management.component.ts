import { Component } from '@angular/core';
import {  OnInit } from '@angular/core';
import { User } from '../user';
import { UsersService } from '../services/users.service';
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
    // Variables pour stocker les données
    users: User[] = [];
    filteredUsers: User[] = [];
    notificationVisible = false;
    currentFilter = 'all';
  
    constructor(
      private usersService: UsersService,
      private router: Router
    ) {}
  
    ngOnInit(): void {
      this.fetchUsers();
    }
  
    // Récupération des utilisateurs depuis le service
    fetchUsers(): void {
      this.usersService.getUsers().subscribe({
        next: (data) => {
          this.users = data.content;
          this.applyFilter();
        },
        error: (error) => console.error('Error fetching users:', error)
      });
    }
  
    // Formatage de l'adresse pour l'affichage
    formatAddress(address: any): string {
      if (!address) return 'Adresse non disponible';
      return `${address.number} ${address.street}, ${address.city} ${address.zipCode}`;
    }
  
    // Gestion des filtres
    setFilter(filter: string): void {
      this.currentFilter = filter;
      this.applyFilter();
    }
  
    // Application du filtre sur la liste des utilisateurs
    applyFilter(): void {
      this.filteredUsers = this.users.filter(user => {
        if (this.currentFilter === 'all') return true;
        return user.roles.some(role => 
          role.name.toLowerCase() === this.currentFilter.toLowerCase()
        );
      });
    }
  
    // Confirmation de suppression avec double vérification
    confirmDelete(userId: number): void {
      if (confirm("Êtes-vous sûr de vouloir supprimer cet utilisateur ?")) {
        if (confirm("Vraiment sûr ? Cette action est irréversible.")) {
          this.deleteUser(userId);
        }
      }
    }
  
    // Suppression d'un utilisateur
    deleteUser(userId: number): void {
      this.usersService.deleteUser(userId).subscribe({
        next: () => {
          this.notificationVisible = true;
          setTimeout(() => this.notificationVisible = false, 3000);
          this.fetchUsers(); // Rafraîchit la liste après suppression
        },
        error: (error) => console.error('Error deleting user:', error)
      });
    }
  
    // Navigation vers la page d'édition
    editUser(userId: number): void {
      this.router.navigate(['/edit', userId]);
    }
  
    // Calcul du pourcentage pour la barre de progression
    calculateRatingPercentage(rating: number): number {
      return (rating / 5) * 100;
    }
  
    // Vérification si un utilisateur est admin
    isAdmin(role: Role): boolean {
      return role.name.toLowerCase() === 'admin';
    }

}


