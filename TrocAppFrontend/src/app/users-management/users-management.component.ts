import { Component } from '@angular/core';
import {  OnInit } from '@angular/core';
import { User } from '../user';
import { UsersService } from '../users.service';
import { NgFor } from '@angular/common';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-users-management',
  standalone: true,
  imports: [NgFor, CommonModule],
  templateUrl: './users-management.component.html',
  styleUrl: './users-management.component.css'
})
export class UsersManagementComponent implements OnInit{
  users: User[] = []; // Tableau d'utilisateurs

  constructor(private usersService: UsersService) {}

  ngOnInit(): void {
    this.usersService.getUsers().subscribe(
      (data) => {
        console.log('API Response:', data);  // Vérifiez ce qui est retourné par l'API
        this.users = data.content;  // Assurez-vous que vous affectez le tableau `content` à `users`
      },
      (error) => {
        console.error('Error fetching users:', error);
      }
    );
  }
}

