import { Address } from './adress.model';
import { Role } from './role';
export interface User {
    id: number; // ID unique de l'utilisateur
    address: Address | null; // Adresse de l'utilisateur
    email: string; // Adresse email
    firstName: string; // Prénom
    lastName: string; // Nom de famille
    password?: string; // Mot de passe (généralement, on ne manipule pas ça en clair)
    rating: number; // Note de l'utilisateur
    roles: Role[]; // Liste des rôles de l'utilisateur
    rolesInput?: string;
    actif ?: boolean; 
  }