export class Address {
    id: number;
    street: string;
    number: string;
    city: string;
    zipCode: number;
    latitude: number;
    longitude: number;
  
    constructor(
      id: number,
      street: string,
      number: string,
      city: string,
      zipCode: number,
      latitude: number,
      longitude: number
    ) {
      this.id = id;
      this.street = street;
      this.number = number;
      this.city = city;
      this.zipCode = zipCode;
      this.latitude = latitude;
      this.longitude = longitude;
    }
  }
  