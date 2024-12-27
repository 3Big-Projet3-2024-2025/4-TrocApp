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


  items: Item[] | undefined;  // Liste d'objets Item associés à la catégorie
  user : User | undefined;  // Administrateur associé à la catégorie

  constructor(
    id_category: number,
    name: string,
    //items: Item[],
    //user: User
  ) {
    this.id_category = id_category;
    this.name = name;
    //this.items = items;
    //this.user=user;

  }
}
