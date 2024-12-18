import { Component } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { ItemService } from '../services/item.service';

@Component({
  selector: 'app-detailed-view-item',
  standalone: true,
  imports: [HttpClientModule],
  templateUrl: './detailed-view-item.component.html',
  styleUrl: './detailed-view-item.component.css'
})
export class DetailedViewItemComponent {

  id_selected_item!: number; // Property for the selected item ID

  item: any; // Property for the item

  // Initialization 
  ngOnInit(): void {
    this.loadDataItem();
  } 

  successMessage: string = ''; // Property for the success message
  errorMessage: string = ''; // Property for the error message
  
  constructor( private itemService: ItemService ) { }

  // Load the data of the selected item
  loadDataItem() {
    this.itemService.getItem(this.id_selected_item).subscribe(
      data => {
        this.item = data;
      },
      error => {
        this.errorMessage = 'Error while trying to fetch the item by ID';
        console.log(error);
      }
    );
  }

  // Function to call when an user would like to do an exchange
    proposeExchange(item: any) { }

}