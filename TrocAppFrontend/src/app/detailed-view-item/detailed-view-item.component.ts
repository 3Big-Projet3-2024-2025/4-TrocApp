import { Component } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { ItemService } from '../services/item.service';
import { UsersService } from '../services/users.service';
import { CategoryService } from '../services/category.service';
import { Item } from '../models/item';
import { User } from '../models/user';
import { Category } from '../models/category.model';

@Component({
  selector: 'app-detailed-view-item',
  standalone: true,
  imports: [HttpClientModule],
  templateUrl: './detailed-view-item.component.html',
  styleUrl: './detailed-view-item.component.css'
})
export class DetailedViewItemComponent {

  id_selected_item!: number; // Property for the selected item ID

  item!: Item; // Property for the item
  owner!: User; // Property for the owner of the item
  category!: Category; // Property for the category of the item

  // Initialization 
  ngOnInit(): void {
    this.loadDataItem();
  } 

  constructor( private itemService: ItemService, private usersService : UsersService, private categoryService : CategoryService ) { }

  // Load the data of the selected item
  loadDataItem() {
    this.itemService.getItem(this.id_selected_item).subscribe({
      next: (data) => {
        this.item = data as Item;
                
        this.categoryService.getCategoryById(this.item.category.id_category).subscribe({
          next: (data: any) => {
            this.category = data;
          },
          error: (error: any) => {
            console.error('Error while trying to fetch the category of the item :', error);
          }
        });

        this.usersService.getUserById(this.item.owner.id).subscribe({
          next: (data: any) => {
            this.owner = data;
          },
          error: (error: any) => {
            console.error('Error while trying to fetch the owner of the item :', error);
          }
        });
      },
      error: (error) => {
        console.error("Error while trying to fetch the data of the item :" + error);
      }
    });
  }

  // Function to call when a user would like to do an exchange
  proposeExchange(id: number) {
    
  }
}