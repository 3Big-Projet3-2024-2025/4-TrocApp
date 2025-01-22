import { Role } from './role';
import { Address } from './address';

export interface User {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  username: string;
  password?: string;
  rating: number;
  address: Address;  
  addressId: number;
  roles: Role[];
  rolesInput?: string;
  blocked: boolean;
  actif: boolean;
}