import { Component } from '@angular/core';
import { GdprRequest } from '../models/gdpr-request.modele';
import { GdprRequestService } from '../services/gdpr-request.service';
import { NgFor, NgIf } from '@angular/common';
import { DatePipe } from '@angular/common'; 
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-gdpr-admin',
  standalone: true,
  imports: [NgFor , DatePipe, NgIf],
  templateUrl: './gdpr-admin.component.html',
  styleUrl: './gdpr-admin.component.css'
})
export class GdprAdminComponent {
 pendingRequests: GdprRequest[] = [];
  processedRequests: GdprRequest[] = [];
  loading = false;
  error = '';
  isAdmin = false;

  constructor(private gdprService: GdprRequestService , private authService: AuthService ) {}

  ngOnInit(): void {
    // Verify if the user is an admin
    this.isAdmin = this.authService.isAdmin();

    // If the user is an admin, load the requests
    if (this.isAdmin) {
      this.loadRequests();
    } else {
      this.error = 'You are not authorized to view GDPR requests'; // Message si l'utilisateur n'est pas admin
    }
  }

  loadRequests(): void {
    this.loading = true;
    this.error = '';
    
    // load the pending requests
    this.gdprService.getPendingRequests().subscribe({
      next: (requests) => {
        this.pendingRequests = requests;
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Error loading pending requests'; // Error loading pending requests
        this.loading = false;
      }
    });

    // load the processed requests
    this.gdprService.getProcessedRequests().subscribe({
      next: (requests) => {
        this.processedRequests = requests;
      },
      error: (error) => {
        this.error = 'Error loading processed requests'; // Error loading processed requests
      }
    });
  }

  async processRequest(request: GdprRequest, approved: boolean): Promise<void> {
    try {
      this.loading = true;
      const response = approved 
        ? ` Request approved: ${request.requesttype}` // Request approved
        : `Request rejected: ${request.requesttype}`; // Request rejected

      // Deactivate the user if the request is approved
      if (approved) {
        await this.gdprService.deactivateUser(request.user.id).toPromise();
      }

      // Update the request status
      await this.gdprService.processRequest(request.id_gdprRequest, response).toPromise();

      // load the requests
      this.loadRequests();
    } catch (error) {
      this.error = 'Error processing request'; // Error processing request
    } finally {
      this.loading = false;
    }
  }

}
