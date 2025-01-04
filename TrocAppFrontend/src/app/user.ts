import { Role } from './role';
import { Address } from './address';
export interface User {
  blocked: any;
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  password?: string;
  rating: number;
  address: Address;  
  addressId: number;
  roles: Role[];
  rolesInput?: string;
}