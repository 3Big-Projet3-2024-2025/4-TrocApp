import { AsyncPipe, DatePipe } from '@angular/common';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { forkJoin, map, Observable, switchMap } from 'rxjs';
import { Exchange } from '../models/exchange';
import { ExchangeService } from '../services/exchange.service';
import { ItemService } from '../services/item.service';
import { Item } from '../models/item';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-exchange-list',
  standalone: true,
  imports: [AsyncPipe, FormsModule, DatePipe],
  templateUrl: './exchange-list.component.html',
  styleUrl: './exchange-list.component.css'
})
export class ExchangeListComponent implements OnInit {

constructor(private exchangeService: ExchangeService, private itemService: ItemService, private authService: AuthService) {}
  
  errorMessage: string = "";

  myExchanges!: Observable<Exchange[]>;

  userId!: number | null;  //l id de la personne connectée

  exchangeToUpdate: Exchange = {
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
        id: -1,
        longitude: -1,
        latitude: -1,
        street: "",
        number: "",
        city: "",
        zipCode: -1
      },
      roles: [],
      username:""
    },
    receiver: {
      id: -1,
      firstName: "",
      lastName: "",
      email: "",
      password: "",
      rating: -1,
      address: {
        id: -1,
        longitude: -1,
        latitude: -1,
        street: "",
        number: "",
        city: "",
        zipCode: -1
      },
      roles: [],
      username: ""
    },
    requestedObject: null,
    offeredObject: null
  }

  acceptanceDate!: string;
  dateConfirmed:boolean | null = null;


   /*explanation code of below
     .pipe(                         used to apply operators like switchMap, map to the observale
      switchmap(exchanges  =>           transforms the initial observable into a new observable
       forkJoin(                          combine many observables and emit their result in array type
            exchanges.map(exchange =>       for each exchange in exchanges, a transformation is applied
              forkJoin([                      2 asynchronous calls are made and forkjoin returns their result in array type
              itemService.get(Rid), iremService.get(Oid)
              ]).pipe(
                map([requestedItem, offeredItem]) => ({   combine data gotten with exchange object
                ...exch,
                requestedObject: requestedItem,       verifies if array, if yes, takes its first value
                offeredObject: offeredItem
                })
              )
      map(updatedExchanges => updatedExchanges)   final transformation done when items added in exchange

    */
  ngOnInit(): void {
    this.userId = this.authService.getIDUserConnected() as number;
    console.log(this.authService.getNameUserConnected())
    console.log(this.userId);
      this.myExchanges = this.exchangeService.getExchangeByUserId(this.userId).pipe(    
      switchMap(exchanges => 
        forkJoin(
          exchanges.map(exchange => 
            forkJoin([
              this.itemService.getItem(exchange.requestedObjectId),
              this.itemService.getItem(exchange.offeredObjectId)
            ]).pipe(
              map(([requestedItem, offeredItem]) => ({ 
                ...exchange, 
                requestedObject: Array.isArray(requestedItem) ? requestedItem[0] :  requestedItem, 
                offeredObject: Array.isArray(offeredItem) ? offeredItem[0] :  offeredItem 
              }))
            )
          )
        )
      ),
      map(updatedExchanges => updatedExchanges)
    );
  }
     

  acceptExchange(exchange: Exchange) {
    const sub = this.exchangeService.getExchange(exchange.id_exchange).subscribe({
      next: (exchangeReceived) => {
        this.exchangeToUpdate = exchangeReceived as Exchange;
        // console.log(exchangeReceived);
        // console.log("recu", this.exchangeToUpdate);
        sub.unsubscribe();
      }, 
      error: (error) => {
        this.errorMessage = error.error;
        sub.unsubscribe();
      }
    });
    exchange.status = "In Progress";
    if(this.dateConfirmed) {
      exchange.acceptanceDate = exchange.proposalDate;
    } else {
      exchange.acceptanceDate = this.acceptanceDate;
    }
    console.log("requested id",exchange.requestedObjectId)
    console.log("offered id", exchange.offeredObjectId)
    console.log("exchange", exchange);
    const subs = this.exchangeService.saveExchange(exchange).subscribe({
      next: (exchange)=> {
        // this.router.navigate(["/itemtest"]);
        this.ngOnInit();
        // console.log("modifie",exchange);
        subs.unsubscribe();
      },
      error: (error) => {
        this.errorMessage = error.error;
        sub.unsubscribe();
      }
    });
  }


  declineExchange(exchange: Exchange) {
    const sub = this.exchangeService.getExchange(exchange.id_exchange).subscribe({
      next: (exchangeReceived) => {
        this.exchangeToUpdate = exchangeReceived as Exchange;
        sub.unsubscribe();
      }, 
      error: (error) => {
        this.errorMessage = error.error;
        sub.unsubscribe();
      }
    });
    exchange.status = "Declined";
   
    const subs = this.exchangeService.saveExchange(exchange).subscribe({
      next: (exchange)=> {
        // this.router.navigate(["/itemtest"]);
        this.ngOnInit();
        subs.unsubscribe();
      },
      error: (error) => {
        this.errorMessage = error.error;
        sub.unsubscribe();
      }
    });
  }


  markAsDone(exchange: Exchange) {
    const sub = this.exchangeService.getExchange(exchange.id_exchange).subscribe({
      next: (exchangeReceived) => {
        this.exchangeToUpdate = exchangeReceived as Exchange;
        sub.unsubscribe();
      }, 
      error: (error) => {
        this.errorMessage = error.error;
        sub.unsubscribe();
      }
    });
    exchange.status = "Completed";

    const subs = this.exchangeService.saveExchange(exchange).subscribe({
      next: (exchange)=> {
        // this.router.navigate(["/itemtest"]);
        this.ngOnInit();
        subs.unsubscribe();
      },
      error: (error) => {
        this.errorMessage = error.error;
        sub.unsubscribe();
      }
    });  }


}
