import { Component } from '@angular/core';
import { GdprRequest } from '../models/gdpr-request.model';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';
import { GdprRequestService } from '../services/gdpr-request.service';

@Component({
  selector: 'app-gdpr-resquest',
  standalone: true,
  imports: [FormsModule, NgIf],
  templateUrl: './gdpr-resquest.component.html',
  styleUrls: ['./gdpr-resquest.component.css']
})
export class GdprResquestComponent {
 // Champs pour le formulaire
 requestType: string = 'Data Deletion'; // La demande est toujours de type suppression
 justification: string = ''; // Justification de la demande
 consent: boolean = false; // Confirmation du consentement

 errorMessage: string = '';
 successMessage: string = '';

 constructor(private gdprRequestService: GdprRequestService) {}

 // Soumission du formulaire
 onSubmit(): void {
   if (this.requestType && this.justification && this.consent) {
     const newRequest: GdprRequest = {
       id_gdprRequest: 0, // L'ID sera généré côté backend
       requestType: this.requestType,
       justification: this.justification,
       consent: this.consent,
       requestDate: new Date().toISOString(), // La date de la demande est générée automatiquement
       status: 'Pending', // Statut initial, en attente
       response: '', // Champ vide par défaut
       responseDate: '', // Champ vide par défaut
       user: { id: 0 } as any // L'ID de l'utilisateur est récupéré via l'authentification
     };

     // Appel au service pour soumettre la demande
     this.gdprRequestService.submitGdprRequest(newRequest).subscribe({
       next: (response) => {
         console.log('Request submitted successfully', response);
         this.successMessage = 'Your GDPR request has been submitted successfully!';
         this.resetForm();
       },
       error: (error) => {
         console.error('Error submitting request', error);
         this.errorMessage = 'Failed to submit the GDPR request. Please try again.';
       }
     });
   } else {
     this.errorMessage = 'Please fill in all required fields.';
   }
 }

 // Annuler le formulaire
 onCancel(): void {
   this.resetForm();
   this.errorMessage = '';
 }

 // Réinitialisation des champs
 resetForm(): void {
   this.justification = '';
   this.consent = false;
 }
}
