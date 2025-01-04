import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../user';
import { UsersService } from '../services/users.service';
import { OnInit } from '@angular/core';
import { NgIf,NgFor } from '@angular/common';
import { Rating } from '../rating';
import { RatingService } from '../services/rating.service';
import { ExchangeService } from '../services/exchange.service'; 
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [NgIf,NgFor, FormsModule],
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.css'
})
export class UserProfileComponent implements OnInit{

  /*user: User | null = null;
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
  }*/

    user: User | null = null;
    userItems: any[] = [];
    isAdmin: boolean = false;
    receivedRatings: Rating[] = [];
    hasExchangeHistory: boolean = false;
    currentUserId: number = 1; // Remplacer par l'ID de l'utilisateur connecté
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
      this.route.params.subscribe(params => {
        if (params['id']) {
          this.loadUser(params['id']);
          this.loadRatings(params['id']);
          this.checkExchangeHistory(this.currentUserId, params['id']);
          
        }
      });
    }
  
    loadUser(userId: number) {
      this.usersService.getUserById(userId).subscribe({
        next: (user) => {
          this.user = user;
          this.loadUserItems(userId);
          if (this.newRating.receiver) {
            this.newRating.receiver.id = userId;
          }
        },
        error: (error) => console.error('Error loading user:', error)
      });
    }
  
    loadRatings(userId: number) {
      this.ratingService.getReceivedRatings(userId).subscribe({
        next: (ratings) => this.receivedRatings = ratings,
        error: (error) => console.error('Error loading ratings:', error)
      });
    }
  
    checkExchangeHistory(currentUserId: number, targetUserId: number) {
      this.exchangeService.getAllExchangesByUserID(currentUserId).subscribe({
        next: (exchanges) => {
          this.hasExchangeHistory = exchanges.some(exchange => 
            exchange.initiator.id === targetUserId || exchange.receiver.id === targetUserId
          );
        },
        error: (error) => console.error('Error checking exchange history:', error)
      });
    }
  
    setRating(stars: number) {
      this.newRating.numberStars = stars;
    }
  
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
          this.receivedRatings.push(rating);
          this.newRating = {
            numberStars: 0,
            comment: '',
            poster: { id: this.currentUserId, firstName: '', lastName: '' },
            receiver: { id: this.user?.id || 0, firstName: '', lastName: '' }
          };
          this.loadRatings(this.user?.id || 0);
        },
        error: (error) => console.error('Error adding rating:', error)
      });
    }

       //Confirm the deletion with double verification
       confirmDelete(ratingId: number, posterId: number): void {
        if (confirm("Are you sure you want to delete this rating?")) {
          if (confirm("Are you absolutely sure? This action is irreversible.")) {
            this.deleteRating(ratingId, posterId);
          }
        }
      }
  
    deleteRating(ratingId: number, posterId: number) {
      if (posterId === this.currentUserId || this.isAdmin) {
        this.ratingService.deleteRating(ratingId).subscribe({
          next: () => this.loadRatings(this.user?.id || 0),
          error: (error) => console.error('Error deleting rating:', error)
        });
      }
    }
  
    loadUserItems(userId: number) {
      this.usersService.getUserItems(userId).subscribe({
        next: (items) => this.userItems = items,
        error: (error) => console.error('Error loading items:', error)
      });
    }

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
}
