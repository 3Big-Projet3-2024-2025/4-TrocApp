import { Component, OnInit } from '@angular/core';
import { ItemService } from '../services/item.service';
import { Observable } from 'rxjs';
import { Item } from '../models/item';
import { AsyncPipe } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-my-items-list',
  standalone: true,
  imports: [AsyncPipe],
  templateUrl: './my-items-list.component.html',
  styleUrl: './my-items-list.component.css'
})
export class MyItemsListComponent implements OnInit {

  constructor(private itemService: ItemService, private router: Router) {}
  
  myItems!: Observable<Item[]>;       //////////////available seulement

  ngOnInit() {
    this.myItems = this.itemService.getItemByUserId(this.userId) as Observable<Item[]>;
  }

  errorMessage = "";

  userId: number = 3;  //l id de la personne connectée


  updateItem(item: Item) {
    this.router.navigate(["/item/"+item.id]);
  }

  deleteItem(item: Item) {
    const sub = this.itemService.deleteItem(item.id).subscribe((itemsList) => {
      this.myItems = itemsList as Observable<Item[]>;
    });
  }

}
