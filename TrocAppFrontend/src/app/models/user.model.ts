export class User {
    id: number;             
    firstName: string;       
    lastName: string;        
    email: string;           
    password: string;        
    addressId: number;       
    rating: number;          
  
   
    constructor(
      id: number, 
      firstName: string, 
      lastName: string, 
      email: string, 
      password: string, 
      addressId: number, 
      rating: number
    ) {
      this.id = id;
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.password = password;
      this.addressId = addressId;
      this.rating = rating;
    }
  }
  