import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { GdprRequest } from '../models/gdpr-request.modele';
import { User } from '../models/user.model';
import { GdprRequestService } from '../services/gdpr-request.service';
import { NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-gdpr-form',
  standalone: true,
  imports: [NgIf, FormsModule],
  templateUrl: './gdpr-form.component.html',
  styleUrl: './gdpr-form.component.css'
})
// noublies pas justification de la demande
export class GdprFormComponent {
 /* newRequest!: GdprRequest;

  constructor(
    private gdprRequestService: GdprRequestService, 
    private authService: AuthService
  ) {}

  ngOnInit() {
    const currentUser = this.authService.getCurrentUser();
    const emptyAdmin = new User(0, '', '', '', '', 0, 0, false);

    this.newRequest = new GdprRequest(
      0,
      '',                // requesttype
      '',                // requestdate
      'Pending',         // status
      '',                // response
      '',                // responsedate
      false,             // consent
      '',                // justification vide par défaut
      currentUser,       // utilisateur courant
      emptyAdmin         // admin avec valeurs par défaut
    );
  }

  submitRequest() {
    if (!this.newRequest.consent) {
      alert('Le consentement est nécessaire pour soumettre une demande GDPR.');
      return;
    }

    if (!this.newRequest.justification || this.newRequest.justification.trim() === '') {
      alert('La justification est nécessaire pour soumettre la demande.');
      return;
    }

    this.newRequest.requestdate = new Date().toISOString();
    this.gdprRequestService.createRequest(this.newRequest).subscribe({
      next: () => {
        alert('Demande soumise avec succès!');
      },
      error: (error) => {
        console.error('Erreur lors de la soumission:', error);
        alert('Erreur lors de la soumission de la demande');
      }
    });
  }

  cancel() {
    const currentUser = this.authService.getCurrentUser();
    const emptyAdmin = new User(0, '', '', '', '', 0, 0, false);

    this.newRequest = new GdprRequest(
      0,
      '',
      '',
      'Pending',
      '',
      '',
      false,
      '',
      currentUser,
      emptyAdmin
    );
  }*/

   newRequest!: GdprRequest;

  constructor(
    private gdprRequestService: GdprRequestService , 
    private HttpClient: HttpClient
  ) {}

  ngOnInit() {
    // Simuler un utilisateur fictif ici
    const currentUser = new User(
      3,                  // ID fictif
      'John',             // Prénom fictif
      'Doe',              // Nom fictif
      'john.doe@example.com',  // Email fictif
      'password123',                 // Mot de passe vide pour le test
      1,                // ID d'adresse fictif
      0,                  // Note fictive
      true                // Utilisateur actif
    );

    // Créer un admin vide avec toutes les propriétés requises (utilisé dans la demande GDPR)
    //const emptyAdmin = new User(0, '', '', '', '', 0, 0, false);

    // Initialiser la nouvelle demande GDPR avec des valeurs par défaut
    this.newRequest = new GdprRequest(
      0,
      '',                // requesttype
      '',                // requestdate
      'Pending',         // status
      '',                // response
      '',                // responsedate
      false,             // consent
      '',                // justification vide par défaut
      currentUser,       // utilisateur fictif
    );
  }

 /* submitRequest() {
    if (this.newRequest.consent) {
      this.newRequest.requestdate = new Date().toISOString();
      this.gdprRequestService.createRequest(this.newRequest).subscribe({
        next: () => {
          alert('Demande soumise avec succès!');
        },
        error: (error) => {
          console.error('Erreur lors de la soumission:', error);
          alert('Erreur lors de la soumission de la demande');
        }
      });
    } else {
      alert('Le consentement est nécessaire pour soumettre une demande GDPR.');
    }
  }*/


    submitRequest() {
      // Validate consent
      if (!this.newRequest.consent) {
        alert('Consent is required to submit a GDPR request.');
        return;
      }
    
      // Validate request type
      if (!this.newRequest.requesttype || this.newRequest.requesttype.trim() === '') {
        alert('Request type is required.');
        return;
      }
    
      // Validate justification
      if (!this.newRequest.justification || this.newRequest.justification.trim() === '') {
        alert('Justification is required for GDPR request.');
        return;
      }
    
      // Set current date for request
      this.newRequest.requestdate = new Date().toISOString();
      
      // Log request before sending
      console.log('Request to be sent:', this.newRequest);
      
      this.gdprRequestService.createRequest(this.newRequest).subscribe({
        next: (response) => {
          console.log('Success response:', response);
          alert('Request submitted successfully!');
        },
        error: (error) => {
          console.error('Detailed error:', error);
          console.log('Error message:', error.error);
          alert('Error submitting request: ' + (error.error?.message || error.message));
        }
      });
    }



  cancel() {
    // Réinitialiser le formulaire avec un utilisateur fictif
    const currentUser = new User(
      1,                  // ID fictif
      'John',             // Prénom fictif
      'Doe',              // Nom fictif
      'john.doe@example.com',  // Email fictif
      '',                 // Mot de passe vide pour le test
      123,                // ID d'adresse fictif
      5,                  // Note fictive
      true                // Utilisateur actif
    );

    const emptyAdmin = new User(0, '', '', '', '', 0, 0, false);

    this.newRequest = new GdprRequest(
      0,
      '',
      '',
      'Pending',
      '',
      '',
      false,
      '',
      currentUser,

    );
  }

}
