import { Component } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { ItemService } from '../services/item.service';
import { UsersService } from '../services/users.service';
import { CategoryService } from '../services/category.service';
import { Item } from '../models/item';
import { User } from '../models/user';
import { Category } from '../models/category.model';
import { ExchangeService } from '../services/exchange.service';
import { Observable } from 'rxjs';
import { AuthService } from '../auth.service';
import { Exchange } from '../models/exchange';
import { ActivatedRoute, Router } from '@angular/router';
import { AsyncPipe, CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-detailed-view-item',
  standalone: true,
  imports: [HttpClientModule, AsyncPipe, FormsModule, CommonModule],
  templateUrl: './detailed-view-item.component.html',
  styleUrl: './detailed-view-item.component.css'
})
export class DetailedViewItemComponent {

  item!: Item; // Property for the item
  owner!: User; // Property for the owner of the item
  category!: Category; // Property for the category of the item

  idSelectedItem!: number; // Property for the selected item ID

  myItems!: Observable<Item[]>;
  userId!: number | null;  //l id de la personne connectée
  receiverId!: number;
  requestedObjectIdCurrent!: number;

  idSelectedItem2!: number;
  
    exchangeToCreate: Exchange = {
      id_exchange: -1,
      offeredObjectId: -1,
      requestedObjectId: -1,
      proposalDate: "",
      acceptanceDate: "",
      status: "",
      firstEvaluation: -1,
      secondEvaluation: -1,
      initiator: {
        id: -1,
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        rating: -1,
        address: {
          id: 0,
          street: '',
          number: '',
          city: '',
          zipCode: 0,
          latitude: 0,
          longitude: 0
        },
        roles: [],
        username: ""
      },
      receiver: {
        id: -1,
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        rating: -1,
        address: {
          id: 0,
          street: '',
          number: '',
          city: '',
          zipCode: 0,
          latitude: 0,
          longitude: 0
        },
        roles: [],
        username: ""
      },
      requestedObject: null,
      offeredObject: null
    }

  // Initialization 
  ngOnInit(): void {
    this.userId = this.authService.getIDUserConnected() as number;
    this.myItems = this.itemService.getItemByUserId(this.userId) as Observable<Item[]>;

    console.log(this.userId);
    console.log(this.myItems);

    this.loadDataItem();
  } 

  constructor( private itemService: ItemService, private usersService : UsersService, private categoryService : CategoryService, private exchangeService: ExchangeService, private authService: AuthService, private router: Router, private route: ActivatedRoute) { }

  loadDataItem() {
    this.route.params.subscribe(params => {
      if(params["id"]) {
        console.log(params["id"]);
        this.idSelectedItem2 = params["id"];
        this.requestedObjectIdCurrent = this.idSelectedItem2;
      }
    });
    this.itemService.getItem(this.idSelectedItem2).subscribe({
      next: (data) => {
        this.item = data as Item;
        console.log(this.item);
        console.log("categ idddd",this.item.category.id_category)

        this.categoryService.getCategoryById(this.item.category.id_category).subscribe({
          next: (data: any) => {
            console.log("categ id",this.item.category.id_category)
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
  proposeExchange(item: Item) {
    this.receiverId = this.item.owner.id;
    this.exchangeToCreate.initiator.id = this.userId as number;
    // console.log("item que j proposeitem);
    this.exchangeToCreate.receiver.id = this.receiverId;
    this.exchangeToCreate.requestedObjectId = this.requestedObjectIdCurrent;
    this.exchangeToCreate.offeredObjectId = item.id;
    this.exchangeToCreate.status = "Proposed"
    // this.exchangeToCreate.proposalDate = this.proposalDate;
    const subs = this.exchangeService.saveExchange(this.exchangeToCreate).subscribe({
      next: (exchange) => {
        this.router.navigate(["/exchanges"]);
        subs.unsubscribe();
      },
      error: (error) => {
        console.log("error: ",error.error);
        subs.unsubscribe();
      }
    })
  }
}