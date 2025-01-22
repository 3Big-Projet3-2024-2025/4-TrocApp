import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../user';
import { UsersService } from '../services/users.service';
import { OnInit } from '@angular/core';
import { NgIf, NgFor, DecimalPipe } from '@angular/common';
import { Rating } from '../rating';
import { RatingService } from '../services/rating.service';
import { ExchangeService } from '../services/exchange.service'; 
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [NgIf, NgFor, FormsModule, DecimalPipe],
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.css'
})
export class UserProfileComponent implements OnInit {

  // User data and associated variables
  user: User | null = null;
  userItems: any[] = [];
  isAdmin: boolean = false;
  receivedRatings: Rating[] = [];
  hasExchangeHistory: boolean = false;
  currentUserId: number = 1; // Replace with the actual logged-in user ID
  newRating: Rating = {
    numberStars: 0,
    comment: '',
    poster: { id: this.currentUserId, firstName: '', lastName: '' },
    receiver: { id: 0, firstName: '', lastName: '' }
  };
  rating: number = 0;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private usersService: UsersService,
    private ratingService: RatingService,
    private exchangeService: ExchangeService
  ) {}

  ngOnInit() {
    // Subscribe to route parameters to load user profile based on user ID
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.loadUser(params['id']);
        this.loadRatings(params['id']);
        this.loadAverageRating(params['id']);
        this.checkExchangeHistory(this.currentUserId, params['id']);
      }
    });
  }

  // Load user details from the UsersService
  loadUser(userId: number) {
    this.usersService.getUserById(userId).subscribe({
      next: (user) => {
        this.user = user;
        this.loadUserItems(userId); // Load user items after fetching user
        if (this.newRating.receiver) {
          this.newRating.receiver.id = userId;
        }
      },
      error: (error) => console.error('Error loading user:', error)
    });
  }

  // Load received ratings of the user
  loadRatings(userId: number) {
    this.ratingService.getReceivedRatings(userId).subscribe({
      next: (ratings) => this.receivedRatings = ratings,
      error: (error) => console.error('Error loading ratings:', error)
    });
  }

  // Check if the current user has exchange history with the target user
  checkExchangeHistory(currentUserId: number, targetUserId: number) {
    this.exchangeService.getAllExchangesByUserID(currentUserId).subscribe({
      next: (exchanges) => {
        this.hasExchangeHistory = exchanges.some(exchange => 
          exchange.initiator.id == targetUserId || exchange.receiver.id == targetUserId
        );
      },
      error: (error) => console.error('Error checking exchange history:', error)
    });
  }

  // Set the rating (number of stars)
  setRating(stars: number) {
    this.newRating.numberStars = stars;
  }

  // Submit a rating for the user
  submitRating() {
    if (!this.hasExchangeHistory) {
      alert('You need to have completed an exchange with this user to leave a rating.');
      return;
    }

    if (!this.newRating.comment || this.newRating.numberStars === 0) {
      alert('Rate and comment are required.');
      return;
    }

    this.ratingService.addRating(this.newRating).subscribe({
      next: (rating) => {
        this.receivedRatings.push(rating); // Add the new rating to the list
        this.newRating = {
          numberStars: 0,
          comment: '',
          poster: { id: this.currentUserId, firstName: '', lastName: '' },
          receiver: { id: this.user?.id || 0, firstName: '', lastName: '' }
        };
        this.loadAverageRating(this.user?.id || 0); // Reload the average rating after submission
      },
      error: (error) => console.error('Error adding rating:', error)
    });
  }

  // Confirm the deletion of a rating with double verification
  confirmDelete(ratingId: number, posterId: number): void {
    if (confirm("Are you sure you want to delete this rating?")) {
      if (confirm("Are you absolutely sure? This action is irreversible.")) {
        this.deleteRating(ratingId, posterId);
      }
    }
  }

  // Delete a rating
  deleteRating(ratingId: number, posterId: number) {
    if (posterId === this.currentUserId || this.isAdmin) {
      this.ratingService.deleteRating(ratingId).subscribe({
        next: () => this.loadAverageRating(this.user?.id || 0),
        error: (error) => console.error('Error deleting rating:', error)
      });
    }
  }

  // Load the items listed by the user
  loadUserItems(userId: number) {
    this.usersService.getUserItems(userId).subscribe({
      next: (items) => this.userItems = items,
      error: (error) => console.error('Error loading items:', error)
    });
  }

  // Toggle the block status of the user
  toggleBlockUser() {
    if (this.user) {
      this.usersService.toggleBlockUser(this.user.id).subscribe({
        next: (updatedUser) => {
          this.user = updatedUser;
          const message = updatedUser.blocked ? 'User has been blocked' : 'User has been unblocked';
          alert(message);
        },
        error: (error) => {
          console.error('Error toggling user block status:', error);
          alert('Error updating user block status');
        }
      });
    }
  }

  // Load the average rating of the user
  loadAverageRating(userId: number) {
    this.ratingService.getAverageRating(userId).subscribe({
      next: (average) => this.rating = average,
      error: (error) => console.error('Error loading average rating:', error)
    });
  }
}
