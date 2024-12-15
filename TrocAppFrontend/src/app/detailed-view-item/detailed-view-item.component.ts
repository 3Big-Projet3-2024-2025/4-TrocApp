import { Component } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-detailed-view-item',
  standalone: true,
  imports: [HttpClientModule],
  templateUrl: './detailed-view-item.component.html',
  styleUrl: './detailed-view-item.component.css'
})
export class DetailedViewItemComponent {

  item: any;

  // Initialization
  /* 
  ngOnInit(): void {
    this.loadDataItem();
  } 
    */
  successMessage: string = ''; // Property for the success message
  errorMessage: string = ''; // Property for the error message
  
  constructor() {
    
  }

  /*
  // Load the data of the selected item
  loadDataItem() {
    this.itemService.getInfosItem().subscribe(
      data => {
        this.item = data;
      },
      error => {
        console.log(error);
      }
    );
  }

  // Function to call when an user would like to do an exchange
  proposeExchange(item: any) {
    this.itemService.proposeExchange(item).subscribe(
      data => {
        this.successMessage = 'Exchange proposed succesfully';
      },
      error => {
        this.errorMessage = 'Error while trying to proposing exchange';
      }
    );
  }
  */

  proposeExchange(item: any) {

  }

}
