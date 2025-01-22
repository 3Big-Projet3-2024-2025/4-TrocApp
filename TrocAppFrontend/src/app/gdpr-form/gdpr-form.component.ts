import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { GdprRequest } from '../models/gdpr-request.modele';
import { User } from '../user';
import { GdprRequestService } from '../services/gdpr-request.service';
import { DatePipe, formatDate, NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../auth.service';
import { UsersService } from '../services/users.service';
import { Router } from '@angular/router';
import { Observable }from 'rxjs';
import moment from 'moment';

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
      id: -1,
      firstName: '',
      lastName: '',
      email: '',
      actif: true
    }
  );
  loading = false;
  error: string | null = null;
  observableUser!: Observable<User>
  currentUser!: User;

  constructor(
    private gdprRequestService: GdprRequestService,
    private userService: UsersService,
    private authService: AuthService,
    private router: Router
    ) {}

  ngOnInit(): void {
    this.checkAndInitializeUser();
  }

  private checkAndInitializeUser(): void {
    this.observableUser = this.userService.getUserById(this.authService.getIDUserConnected() as number) as Observable<User>;

    if (!this.observableUser) {
      this.router.navigate(['/auth/login'], { 
        queryParams: { returnUrl: '/gdpr/request' } 
      });
      return;
    }

    this.observableUser.subscribe(user => {

      console.log(user);

      this.currentUser = user as User;

      this.newRequest.user.id = this.currentUser.id;
      this.newRequest.user.firstName = this.currentUser.firstName;
      this.newRequest.user.lastName = this.currentUser.lastName;
      this.newRequest.user.email = this.currentUser.email;
    });
  }

  submitRequest(): void {
    if (!this.currentUser) {
      this.error = 'Please log in to submit a request';
      return;

    }

    //this.newRequest.user.firstName = this.currentUser.firstName;
    //this.newRequest.user.lastName = this.currentUser.lastName;
    //this.newRequest.user.email = this.currentUser.email;

    if (!this.validateRequest()) {
      return;
    }

    this.error = null;
    this.loading = true;
    this.newRequest.requestdate = moment().format("yyyy-MM-DD HH:mm:ss");

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