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
  newRequest: GdprRequest = new GdprRequest(
    0, '', '', 'Pending', '', '', false, '', {
      id: 0,
      firstName: '',
      lastName: '',
      email: '',
      actif: true
    }
  );
  loading = false;
  error: string | null = null;
  currentUser: any = null;

  constructor(
    private gdprRequestService: GdprRequestService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.checkAndInitializeUser();
  }

  private checkAndInitializeUser(): void {
    const tokenDecoded = this.authService.decodeToken();
    this.currentUser = tokenDecoded ? tokenDecoded : null;

    if (!this.currentUser) {
      this.router.navigate(['/auth/login'], { 
        queryParams: { returnUrl: '/gdpr/request' } 
      });
      return;
    }

    this.newRequest.user = this.currentUser;
  }

  submitRequest(): void {
    if (!this.currentUser) {
      this.error = 'Please log in to submit a request';
      return;

    }

    this.newRequest.user.firstName = this.currentUser.firstName;
    this.newRequest.user.lastName = this.currentUser.lastName;
    this.newRequest.user.email = this.currentUser.email;

    if (!this.validateRequest()) {
      return;
    }

    this.error = null;
    this.loading = true;
    this.newRequest.requestdate = new Date().toISOString();

    this.gdprRequestService.createRequest(this.newRequest).subscribe({
      next: (response: GdprRequest) => {
        console.log('Request submitted successfully:', response);
        alert('Request submitted successfully!');
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

  private validateRequest(): boolean {
    if (!this.newRequest.consent) {
      this.error = 'Consent is required to submit a GDPR request.';
      this.loading = false;
      return false;
    }

    if (!this.newRequest.requesttype?.trim()) {
      this.error = 'The type of request is required.';
      this.loading = false;
      return false;
    }

    if (!this.newRequest.justification?.trim()) {
      this.error = 'Justification is required for the GDPR request.';
      this.loading = false;
      return false;
    }

    return true;
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
