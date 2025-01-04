import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { User } from '../user';
import { UsersService } from '../services/users.service';
import { NgFor } from '@angular/common';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { Role } from '../role';
import { ActivatedRoute, Router } from '@angular/router';
import { RatingService } from '../services/rating.service';
import { Rating } from '../rating';

@Component({
  selector: 'app-users-management',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './users-management.component.html',
  styleUrl: './users-management.component.css'
})
export class UsersManagementComponent implements OnInit {
  
  users: User[] = []; // List of users
  filteredUsers: User[] = []; // List of users after filter is applied
  notificationVisible = false; // Flag for showing notification after delete
  currentFilter = 'all'; // Current filter selection (e.g. roles)
  rating: number = 0; 
  receivedRatings: Rating[] = [];
  userAverageRatings = new Map<number, number>(); // Map of user average ratings
  
  constructor(
    private route: ActivatedRoute,
    private usersService: UsersService,
    private router: Router,
    private ratingService: RatingService,
  ) {}

  ngOnInit(): void {
    this.fetchUsers(); // Fetch users when the component is initialized
  }

  // Fetching the list of users from the service
  fetchUsers(): void {
    this.usersService.getUsers().subscribe({
      next: (data) => {
        this.users = data.content; // Storing the users
        this.applyFilter(); // Apply the current filter
        this.users.forEach(user => {
          this.loadAverageRating(user.id); // Load the average rating for each user
        });
      },
      error: (error) => console.error('Error fetching users:', error)
    });
  }

  // Fetch the average rating of a specific user
  loadAverageRating(userId: number) {
    this.ratingService.getAverageRating(userId).subscribe({
      next: (average) => {
        this.userAverageRatings.set(userId, average); // Store the average rating
      },
      error: (error) => console.error('Error loading average rating:', error)
    });
  }

  // Helper method to get the average rating of a user
  getUserAverageRating(userId: number): number {
    return this.userAverageRatings.get(userId) || 0; // Return 0 if no rating is available
  }

  // Format the address of a user for display
  formatAddress(address: any): string {
    if (!address) return 'Address not available';
    return `${address.number} ${address.street}, ${address.city} ${address.zipCode}`;
  }

  // Set the filter based on user role or all users
  setFilter(filter: string): void {
    this.currentFilter = filter; // Set the current filter value
    this.applyFilter(); // Apply the filter
  }

  // Apply the filter to the list of users based on the selected filter
  applyFilter(): void {
    this.filteredUsers = this.users.filter(user => {
      if (this.currentFilter === 'all') return true; // Show all users
      return user.roles.some(role => 
        role.name.toLowerCase() === this.currentFilter.toLowerCase() // Filter based on role
      );
    });
  }

  // Confirm deletion with double verification
  confirmDelete(userId: number): void {
    if (confirm("Are you sure you want to delete this user?")) {
      if (confirm("Are you absolutely sure? This action is irreversible.")) {
        this.deleteUser(userId); // Delete user if confirmed
      }
    }
  }

  // Deleting a user from the system
  deleteUser(userId: number): void {
    this.usersService.deleteUser(userId).subscribe({
      next: () => {
        this.notificationVisible = true; // Show notification on success
        setTimeout(() => this.notificationVisible = false, 3000); // Hide notification after 3 seconds
        this.fetchUsers(); // Refresh the list of users after deletion
      },
      error: (error) => console.error('Error deleting user:', error)
    });
  }

  // Navigate to the edit page for the selected user
  editUser(userId: number): void {
    this.router.navigate(['/edit', userId]); // Redirect to the edit page
  }

  // Calculate the percentage for displaying rating progress bar
  calculateRatingPercentage(rating: number): number {
    return (rating / 5) * 100; // Assuming rating is on a scale of 1 to 5
  }

  // Check if the user has an admin role
  isAdmin(role: Role): boolean {
    return role.name.toLowerCase() === 'admin'; // Check if the role is admin
  }
}
