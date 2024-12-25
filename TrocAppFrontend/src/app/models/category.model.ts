// category.model.ts

import { Item } from './item.model'; 
import { User } from './user.model';

export class Category {
  id_category: number;
  name: string;
 
  constructor(
    id_category: number,
    name: string,
    
  ) {
    this.id_category = id_category;
    this.name = name;
    
  }
}
