export class User {
    id: number;             
    firstName: string;       
    lastName: string;        
    email: string;           
    password: string;        
    addressId: number;       
    rating: number;  
    actif: boolean = true; // Par défaut, un utilisateur est actif        
  
   
    constructor(
      id: number, 
      firstName: string, 
      lastName: string, 
      email: string, 
      password: string, 
      addressId: number, 
      rating: number,
      actif: boolean
    ) {
      this.id = id;
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.password = password;
      this.addressId = addressId;
      this.rating = rating;
      this.actif = actif;
    }
  }
  