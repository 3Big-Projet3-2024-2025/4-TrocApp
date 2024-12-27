import { Component } from '@angular/core';
import { latLng, tileLayer, Marker, icon, Map } from 'leaflet';
import { HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { ItemService } from '../services/item.service';
import { AddressService } from '../services/address.service';
import { UsersService } from '../services/users.service';
import { Item } from '../models/item';
import { User } from '../models/user';

import { Address } from '../address';
import { Address } from '../models/address.model';

import { Address } from '../models/adress.model';


@Component({
  selector: 'app-viewing-items-map',
  templateUrl: './viewing-items-map.component.html',
  styleUrls: ['./viewing-items-map.component.css'],
  standalone: true,
  imports: [LeafletModule,HttpClientModule],
})
export class ViewingItemsMapComponent {
 items: Item[] = []; // Property for the items
  owner!: User; // Property for the owner
  address!: Address; // Property for the address
  map!: Map; // Property for the map
  options: any; // Property for the options

  constructor(private router: Router, private itemService: ItemService, private addressService: AddressService, private userService: UsersService) {
    this.options = {
      // Construct the layer for the map (with credits)
      layers: [
        tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
          maxZoom: 18,
          attribution: '© OpenStreetMap contributors',
        }),
      ],
      zoom: 14,
      // Center of the map (HELHa Montignies-sur-Sambre)
      center: latLng(50.4089, 4.4796),
    };
  }

  // Load the map and the markers
  onMapReady(map: Map): void {
    this.map = map;
    this.loadMarkers();
  }

  loadMarkers(): void {
    // Load the items from the database
    this.itemService.getAvailableItem().subscribe(
      (data: any) => {
        this.items = data;

        // For each item, fetch the owner and the address
        this.items.forEach((item) => {
          // Fetch the owner of the item
          this.userService.getUserById(item.owner.id).subscribe({
            next: (data: any) => {
              this.owner = data;
              console.log(this.owner);

              // Check if the owner has an address with a valid id
              const addressId = this.owner?.address?.id;
              if (addressId !== undefined) {
                // Fetch the address of the owner
                this.addressService.getAddressById(addressId).subscribe({
                  next: (data: any) => {
                    this.address = data;
                    // Create a marker for the item
                    const marker = new Marker([this.address.latitude, this.address.longitude], {
                      icon: icon({
                        iconSize: [35, 35],
                        iconAnchor: [13, 41],
                        iconUrl: 'assets/marker-icon.png',
                      }),
                    }).on('click', () => {
                      this.router.navigate(['/detailed-view-item', item.id]);
                    });
                    marker.addTo(this.map);
                  },
                  error: (error: any) => {
                    console.error('Error fetching address:', error);
                  }
                });
              } else {
                console.warn('Owner address id is undefined for item:', item.id);
              }
            },
            error: (error: any) => {
              console.error('Error fetching owner:', error);
            }
          });
        });
      },
      (error: any) => {
        console.error('Error fetching items:', error);
      }
    );
  }

}
