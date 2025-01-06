import { Component, OnInit } from '@angular/core';
import { ItemService } from '../services/item.service';
import { Observable } from 'rxjs';
import { Item } from '../models/item';
import { AsyncPipe } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-my-items-list',
  standalone: true,
  imports: [AsyncPipe],
  templateUrl: './my-items-list.component.html',
  styleUrl: './my-items-list.component.css'
})
export class MyItemsListComponent implements OnInit {

  constructor(private itemService: ItemService, private router: Router, private authService: AuthService) {}
  
  myItems!: Observable<Item[]>;       //////////////available seulement

  userId!: number | null;  //l id de la personne connectée

  ngOnInit() {
    this.userId = this.authService.getIDUserConnected() as number;
    this.myItems = this.itemService.getItemByUserId(this.userId) as Observable<Item[]>;
  }

  errorMessage = "";


  updateItem(item: Item) {
    this.router.navigate(["/item/"+item.id]);
  }

  deleteItem(item: Item) {
    const sub = this.itemService.deleteItem(item.id).subscribe(() => {
      this.myItems = this.itemService.getItemByUserId(this.userId as number) as Observable<Item[]>;
    });
  }

}
