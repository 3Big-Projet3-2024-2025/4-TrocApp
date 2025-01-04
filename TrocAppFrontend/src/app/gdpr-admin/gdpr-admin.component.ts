import { Component } from '@angular/core';
import { GdprRequest } from '../models/gdpr-request.modele';
import { GdprRequestService } from '../services/gdpr-request.service';
import { NgFor } from '@angular/common';
import { DatePipe } from '@angular/common'; 

@Component({
  selector: 'app-gdpr-admin',
  standalone: true,
  imports: [NgFor , DatePipe],
  templateUrl: './gdpr-admin.component.html',
  styleUrl: './gdpr-admin.component.css'
})
export class GdprAdminComponent {
  pendingRequests: GdprRequest[] = [];  // Stores pending requests
  treatedRequests: GdprRequest[] = [];  // Stores processed requests

  constructor(private gdprRequestService: GdprRequestService) {}

  ngOnInit(): void {
    this.loadRequests();
  }

  // Load pending and processed requests
  loadRequests() {
    this.gdprRequestService.getPendingRequests().subscribe({
      next: (requests) => {
        this.pendingRequests = requests;
      },
      error: (err) => {
        console.error('Error loading pending requests', err);
      }
    });

    this.gdprRequestService.getTreatedRequests().subscribe({
      next: (requests) => {
        this.treatedRequests = requests;
      },
      error: (err) => {
        console.error('Error loading processed requests', err);
      }
    });
  }

  // Deactivate a user
  /*deactivateUser(userId: number) {
    this.gdprRequestService.deactivateUser(userId).subscribe({
      next: (response) => {
        alert('User has been successfully deactivated'); // Confirm deactivation
        this.loadRequests(); // Refresh the request lists
      },
      error: (err) => {
        console.error('Error deactivating the user', err);
        alert('Error deactivating the user'); // Show error message if deactivation fails
      }
    });
  }*/
 // Modify deactivateUser method to handle both user deactivation and request update
deactivateUser(request: GdprRequest) { 
  this.gdprRequestService.deactivateUser(request.user.id).subscribe({
    next: () => {
      // After successful deactivation, update the request status
      this.gdprRequestService.updateRequestStatus(
        request.id_gdprRequest,
        'Completed',
        'User account has been deactivated as requested'
      ).subscribe({
        next: () => {
          alert('Request processed and user deactivated successfully');
          this.loadRequests();  // Reload both lists
        },
        error: (err) => {
          console.error('Error updating request status', err);
          alert('User deactivated but error updating request status');
        }
      });
    },
    error: (err) => {
      console.error('Error deactivating user', err);
      alert('Error processing request');
    }
  });
}

}
