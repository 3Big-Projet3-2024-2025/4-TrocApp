import { Address } from './adress.model';
import { Role } from './role';
export interface User {
    id: number;
    address: Address | null;
    email: string; 
    firstName: string;
    lastName: string;
    username: string;
    password?: string;
    rating: number; 
    roles: Role[];
    rolesInput?: string;
  }