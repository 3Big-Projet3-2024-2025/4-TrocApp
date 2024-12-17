// item.model.ts

import { Category } from './category.model';  
import { User } from './user.model';  

export class Item {
  id: number;               
  name: string;             
  description: string;      
  photo: string;             
  category: Category;        
  available: boolean = true; 
  owner: User;               

  
  constructor(
    id: number,
    name: string,
    description: string,
    photo: string,
    category: Category,
    available: boolean,
    owner: User
  ) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.photo = photo;
    this.category = category;
    this.available = available;
    this.owner = owner;
  }
}
