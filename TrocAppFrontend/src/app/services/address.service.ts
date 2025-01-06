import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError, of } from 'rxjs';
import { environment } from '../../environments/environment';
import { map} from 'rxjs/operators';
import { CookieService } from 'ngx-cookie-service';

export interface AddressSuggestion {
  street: string;
  city: string;
  zipCode: string;
  number?: string;
  formatted: string;
}

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  private baseUrl = 'http://localhost:8080/addresses';
  private readonly OPENCAGE_API_URL = 'https://api.opencagedata.com/geocode/v1/json';
  private readonly OPENCAGE_API_KEY = environment.openCageApiKey; 

  constructor(private httpClient : HttpClient, private cookieService: CookieService) { }

  private getAuthHeaders(): HttpHeaders {
    const token = this.cookieService.get('token');
    return new HttpHeaders({
      Authorization: 'Bearer ' + token
    });
  }
    getAddressById(id: number): Observable<any> {
      const headers = this.getAuthHeaders();
      return this.httpClient.get(`${this.baseUrl}/${id}`, { headers } ).pipe(
        catchError((error) => {
          console.error(`Error fetching address with ID ${id}:`, error);
          return throwError(() => new Error(`Error fetching address with ID ${id}.`));
        })
      );
    }


    searchAddress(query: string): Observable<AddressSuggestion[]> {
      if (!query || query.length < 3) {
        return of([]);
      }
  
      const params = {
        q: `${query}, Belgium`, 
        key: this.OPENCAGE_API_KEY,
        countrycode: 'BE',
        limit: '5',
        language: 'fr', 
        no_annotations: '1'
      };
  
      return this.httpClient.get(this.OPENCAGE_API_URL, { params }).pipe(
        map((response: any) => {
          return response.results.map((result: any) => {
            const components = result.components;
            return {
              street: components.road || components.street || '',
              city: components.city || components.town || components.village || '',
              zipCode: components.postcode || '',
              number: components.house_number || '',
              formatted: result.formatted
            };
          });
        }),
        catchError(error => {
          console.error('Error fetching address suggestions:', error);
          return of([]);
        })
      );
    }
  
    
    validateBelgianAddress(address: string): Observable<boolean> {
      const params = {
        q: address,
        key: this.OPENCAGE_API_KEY,
        countrycode: 'BE',
        limit: '1'
      };
  
      return this.httpClient.get(this.OPENCAGE_API_URL, { params }).pipe(
        map((response: any) => {
          return response.results.length > 0 && 
                 response.results[0].components.country_code === 'be';
        }),
        catchError(() => of(false))
      );
    }

}
