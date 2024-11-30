import { Role } from './role';
export interface User {
    id: number; // ID unique de l'utilisateur
    address: string; // Adresse de l'utilisateur
    email: string; // Adresse email
    firstName: string; // Prénom
    lastName: string; // Nom de famille
    password: string; // Mot de passe (généralement, on ne manipule pas ça en clair)
    rating: number; // Note de l'utilisateur
    addressId: number; // ID de l'adresse (si lié à une autre table)
    roles: Role[]; // Liste des rôles de l'utilisateur
  }