import { Role } from './role';
import { Address } from './address';
export interface User {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  password?: string;
  rating: number;
  address: Address;  // L'adresse de l'utilisateur est maintenant un objet
  addressId: number;
  roles: Role[];
  rolesInput?: string;
}