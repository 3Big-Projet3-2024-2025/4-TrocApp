import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../user';
import { UsersService } from '../services/users.service';
import { OnInit } from '@angular/core';
import { NgIf,NgFor } from '@angular/common';

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [NgIf,NgFor],
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.css'
})
export class UserProfileComponent implements OnInit{

  user: User | null = null;
  userItems: any[] = []; // Type this properly based on your Item interface
  isAdmin: boolean = false; // Set this based on your auth logic

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private usersService: UsersService
  ) {}

  ngOnInit() {
    // Get user ID from route parameters
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.loadUser(params['id']);
      }
    });
  }


  loadUser(userId: number) {
    this.usersService.getUserById(userId).subscribe({
      next: (user) => {
        this.user = user;
        this.loadUserItems(userId);
      },
      error: (error) => {
        console.error('Error loading user:', error);
        // Handle error - maybe redirect to error page
      }
    });
  }

  loadUserItems(userId: number) {
    // Implement this method based on your items service
    // this.itemsService.getUserItems(userId).subscribe(items => this.userItems = items);
  }

  editUser() {
    if (this.user) {
      this.router.navigate(['/user-edit', this.user.id]);
    }
  }

  deleteUser() {
    if (this.user && confirm('Are you sure you want to delete this user?')) {
      this.usersService.deleteUser(this.user.id).subscribe({
        next: () => {
          // Redirect to users list after successful deletion
          this.router.navigate(['/users-management']);
        },
        error: (error) => {
          console.error('Error deleting user:', error);
          // Handle error
        }
      });
    }
  }

}
