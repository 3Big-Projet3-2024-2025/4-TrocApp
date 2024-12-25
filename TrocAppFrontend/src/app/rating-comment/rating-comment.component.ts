import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Rating } from '../rating';
import { RatingService } from '../services/rating.service';
import { NgFor, NgIf, NgClass } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-rating-comment',
  standalone: true,
  imports: [NgFor, NgIf,FormsModule],
  templateUrl: './rating-comment.component.html',
  styleUrl: './rating-comment.component.css'
})
export class RatingCommentComponent implements OnInit {
  userId: number = 2; // ID de l'utilisateur qui reçoit les commentaires
  receivedRatings: Rating[] = [];
  newRating: Rating = {
    numberStars: 0,
    comment: '',
    poster: { id: 1, firstName: 'John', lastName: 'Doe' }, // Exemple de poster
    receiver: { id: 2, firstName: 'Jane', lastName: 'Smith' } // Exemple de receiver
  };

  constructor(private ratingService: RatingService) {}

  ngOnInit(): void {
    this.loadReceivedRatings();
  }

  loadReceivedRatings(): void {
    this.ratingService.getReceivedRatings(this.userId).subscribe(ratings => {
      this.receivedRatings = ratings;
    });
  }

  addRating(): void {
    this.ratingService.addRating(this.newRating).subscribe(() => {
      this.newRating.comment = '';
      this.newRating.numberStars = 0;
      this.loadReceivedRatings();
    });
  }

  deleteRating(ratingId: number): void {
    this.ratingService.deleteRating(ratingId).subscribe(() => {
      this.loadReceivedRatings();
    });
  }

  updateRating(rating: Rating): void {
    this.ratingService.updateRating(rating.id!, rating).subscribe(() => {
      this.loadReceivedRatings();
    });
  }
}
