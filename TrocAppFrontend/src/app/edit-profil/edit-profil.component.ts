import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from '../user';
import { Role } from '../role';
import { UsersService } from '../services/users.service';
import { OnInit } from '@angular/core';
import { NgIf } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { AddressService } from '../services/address.service';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { AddressSuggestion } from '../services/address.service';

@Component({
  selector: 'app-edit-profil',
  standalone: true,
  imports: [NgIf,ReactiveFormsModule],
  templateUrl: './edit-profil.component.html',
  styleUrl: './edit-profil.component.css'
})
export class EditProfilComponent implements OnInit {
  profileForm: FormGroup;  // Reactive form for editing the profile
  user: User | null = null;  // Loaded user
  roles: Role[] = [];  // List of available roles
  isEditing = false;  // Flag to indicate if editing is enabled
  private readonly TEMP_USER_ID = 1;  // Temporary ID until authentication
  addressSuggestions: AddressSuggestion[] = [];  // Address suggestions
  message: string | null = null;  // Status message
  success: boolean = false;  // Flag to indicate success of the operation

  constructor(
    private fb: FormBuilder,
    private usersService: UsersService,
    private addressService: AddressService
  ) {
    // Initialize form with necessary fields and validation rules
    this.profileForm = this.fb.group({
      firstName: ['', [Validators.required, Validators.minLength(2)]],
      lastName: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      address: this.fb.group({
        street: ['', Validators.required],
        number: ['', Validators.required],
        city: ['', Validators.required],
        zipCode: ['', [Validators.required, Validators.pattern(/^[1-9][0-9]{3}$/)]],
      })
    });
  }

  ngOnInit() {
    this.loadUserProfile();  // Load user profile on component initialization
    this.setupAddressAutocomplete();  // Setup address autocomplete functionality
  }

  private loadUserProfile() {
    // Fetch user data from service (using temporary ID)
    this.usersService.getUserById(this.TEMP_USER_ID).subscribe({
      next: (user) => {
        this.user = user;
        // Populate the form with user data
        this.profileForm.patchValue({
          firstName: user.firstName,
          lastName: user.lastName,
          email: user.email,
          address: {
            street: user.address?.street || '',
            number: user.address?.number || '',
            city: user.address?.city || '',
            zipCode: user.address?.zipCode || ''
          }
        });
        this.loadRoles();  // Load roles after fetching the user data
      },
      error: (error) => {
        console.error('Error loading user profile:', error);  // Handle errors
      }
    });
  }

  loadRoles() {
    // Load available roles from the service
    this.usersService.getAllRoles().subscribe({
      next: (roles) => this.roles = roles,
      error: (error) => console.error('Error loading roles:', error)  // Handle errors
    });
  }

  searchAddress(query: string) {
    // If the query is too short, clear address suggestions
    if (!query || query.length < 3) {
      this.addressSuggestions = [];
      return;
    }

    // Replace with actual API call to OpenCage
    const apiKey = '38de54870196476ba1dd072ad28a4d51';
    const country = 'BE';  // Restrict to Belgium
    
    fetch(`https://api.opencagedata.com/geocode/v1/json?q=${query}&countrycode=${country}&key=${apiKey}`)
      .then(response => response.json())
      .then(data => {
        // Map API response to the format of address suggestions
        this.addressSuggestions = data.results.map((result: any) => ({
          street: result.components.road,
          city: result.components.city,
          zipCode: result.components.postcode
        }));
      })
      .catch(error => console.error('Error fetching address suggestions:', error));  // Handle API errors
  }

  private setupAddressAutocomplete() {
    // Setup reactive form to listen for address input changes and fetch suggestions
    this.profileForm.get('address.street')?.valueChanges.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(value => this.addressService.searchAddress(value))
    ).subscribe(suggestions => {
      this.addressSuggestions = suggestions;
    });
  }

  selectAddress(suggestion: AddressSuggestion) {
    // Populate the form with the selected address suggestion
    this.profileForm.patchValue({
      address: {
        street: suggestion.street,
        number: suggestion.number || '',
        city: suggestion.city,
        zipCode: suggestion.zipCode
      }
    });
    this.addressSuggestions = [];  // Clear suggestions after selection
  }

  toggleEdit() {
    // Toggle edit mode on/off
    this.isEditing = !this.isEditing;
    if (!this.isEditing && this.user) {
      // Reset form to current user data when canceling edit
      this.profileForm.patchValue(this.user);
    }
  }

  onSubmit() {
    // Submit the form if it's valid and the user is loaded
    if (this.profileForm.valid && this.user) {
      const updatedUser = {
        ...this.user,
        ...this.profileForm.value,
        id: this.TEMP_USER_ID
      };
  
      // Call service to update user profile
      this.usersService.updateUser(updatedUser).subscribe({
        next: (response) => {
          this.user = response;
          this.isEditing = false;
          this.message = 'Profile successfully updated.';
          this.success = true;
          // Optionally, clear the message after a few seconds
          setTimeout(() => {
            this.message = null;
          }, 3000);
        },
        error: (error) => {
          console.error('Error updating profile:', error);
          this.message = 'Error in updating profile.';
          this.success = false;
          setTimeout(() => {
            this.message = null;
          }, 3000);
        }
      });
    }
  }
}
