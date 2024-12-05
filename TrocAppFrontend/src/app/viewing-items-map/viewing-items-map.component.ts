import { Component } from '@angular/core';
import { latLng, tileLayer, Marker, icon, Map } from 'leaflet';
import { Router } from '@angular/router';
import { LeafletModule } from '@asymmetrik/ngx-leaflet'; 

@Component({
  selector: 'app-viewing-items-map',
  templateUrl: './viewing-items-map.component.html',
  styleUrls: ['./viewing-items-map.component.css'],
  standalone: true, 
  imports: [LeafletModule],
})
export class ViewingItemsMapComponent {
  items: any[] = [];
  map!: Map;
  options: any;


  constructor(private router: Router) {
    this.options = {
      // Construct the layer for the map (with credits)
      layers: [
        tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
          maxZoom: 18,
          attribution: '© OpenStreetMap contributors',
        }),
      ],
      zoom: 13,
      // Center of the map (HELHa Montignies-sur-Sambre)
      center: latLng(50.4089, 4.4796),
    };
  }

  // Load the map and the markers
  onMapReady(map: Map): void {
    this.map = map;
    this.loadMarkers();
  }

  // Load the markers
  loadMarkers(): void {
    // Mock data to display on the map (I don't have the necessary classes yet (Others Use-Cases))
    this.items = [
      {
        id: 1,
        name: 'Objet 1',
        address: { latitude: 50.409, longitude: 4.477 },
      },
      {
        id: 2,
        name: 'Objet 2',
        address: { latitude: 50.408, longitude: 4.480 },
      },
    ];

    // Add a marker for each item that are available
      this.items.forEach((item) => {
      const marker = new Marker([item.address.latitude, item.address.longitude], {
        icon: icon({
          iconSize: [35, 35],
          iconAnchor: [13, 41],
          iconUrl: 'assets/marker-icon.png',
        }),
        // Load the detailed view of an item when selected
      }).on('click', () => {
        this.router.navigate(['/detailed-view-item', item.id]);
      });

      marker.addTo(this.map);
    }); 
  }
}