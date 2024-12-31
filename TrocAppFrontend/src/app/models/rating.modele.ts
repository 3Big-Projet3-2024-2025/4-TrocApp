// rating.model.ts
export interface Rating {
    id: number;           
    numberStars: number; 
    comment: string;     
   
    poster: User;
    receiver: User;
  }
  
 
  export interface User {
    id: number;          
    firstName: string;   
    lastName: string;     
    email: string;       
    password: string;     
    address: Address;     
    rating: number;       
 
    postedRatings: Rating[];
    receivedRatings: Rating[];
  }
  
  
  export interface Address {
    street: string;       
    number: string;      
    city: string;         
    zipCode: string;      
    latitude: number;     
    longitude: number;    
  }
  