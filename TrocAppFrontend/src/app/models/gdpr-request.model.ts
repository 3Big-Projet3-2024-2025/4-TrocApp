import { User } from './user.model';  

export class GdprRequest {
  id_gdprRequest: number;
  requestType: string;
  justification: string;
  consent: boolean;
  requestDate: string;  
  status: string;
  response: string;
  responseDate: string;   
  user: { id: number };

  
  constructor(
    id_gdprRequest: number,
    requestType: string,
    justification: string,
    consent: boolean,
    requestDate: string,
    status: string,
    response: string,
    responseDate: string,
    user: { id: number }
  ) {
    this.id_gdprRequest = id_gdprRequest;
    this.requestType = requestType;
    this.justification = justification;
    this.consent = consent;
    this.requestDate = requestDate;
    this.status = status;
    this.response = response;
    this.responseDate = responseDate;
    this.user = user;
  }
}
