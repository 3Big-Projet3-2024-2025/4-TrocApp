import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { GdprRequest } from '../models/gdpr-request.modele';
import { User } from '../models/user.model';
import { GdprRequestService } from '../services/gdpr-request.service';
import { NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-gdpr-form',
  standalone: true,
  imports: [NgIf, FormsModule],
  templateUrl: './gdpr-form.component.html',
  styleUrl: './gdpr-form.component.css'
})
export class GdprFormComponent {
 newRequest!: GdprRequest;
  loading = false;
  error: string | null = null;
  currentUser: any = null; 

  constructor(
    private gdprRequestService: GdprRequestService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Verify if user is logged in
    const tokenDecoded = this.authService.decodeToken();
    this.currentUser = tokenDecoded ? tokenDecoded : null;

    if (!this.currentUser) {
      this.router.navigate(['/login'], { queryParams: { returnUrl: '/gdpr/request' } });
      return;
    }

    this.newRequest = new GdprRequest(
      0, '', '', 'Pending', '', '', false, '', this.currentUser
    );
  }

  submitRequest(): void {
    if (!this.currentUser) {
      this.error = ' Please log in to submit a request'; //
      return;
    }

    this.error = null;
    this.loading = true;

    // Validation
    if (!this.newRequest.consent) {
      this.error = 'Consent is required to submit a GDPR request.';
      this.loading = false;
      return;
    }

    if (!this.newRequest.requesttype?.trim()) {
      this.error = ' the type of request is required.'; 
      this.loading = false;
      return;
    }

    if (!this.newRequest.justification?.trim()) {
      this.error = 'justification is required for the GDPR request.'; // 
      this.loading = false;
      return;
    }

    this.newRequest.requestdate = new Date().toISOString();

    // Submit the request
    this.gdprRequestService.createRequest(this.newRequest).subscribe({
      next: (response: GdprRequest) => {
        console.log(' Request submitted successfully:', response); // Request submitted successfully
        alert(' Request submitted successfully!');
        this.loading = false;
        this.resetForm();
      },
      error: (error: any) => {
        console.error('Error during submission:', error); 
        this.error = error.error?.message || 'An error occurred while submitting the request'; 
        this.loading = false;
      }
    });
  }

  resetForm(): void {
    if (this.currentUser) {
      this.newRequest = new GdprRequest(
        0,
        '',
        '',
        'Pending',
        '',
        '',
        false,
        '',
        this.currentUser
      );
    }
  }

  cancel(): void {
    this.resetForm();
  }
   

}
