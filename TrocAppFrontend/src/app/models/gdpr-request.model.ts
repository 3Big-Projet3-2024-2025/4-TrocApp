// gdpr-request.model.ts


import { User } from './user.model';  

export class GdprRequest {
  id_gdprRequest: number;
  requestType: string;
  requestDate: string;  
  status: string;
  response: string;
  responseDate: string;   
  user: User;  

  
  constructor(
    id_gdprRequest: number,
    requestType: string,
    requestDate: string,
    status: string,
    response: string,
    responseDate: string,
    user: User
  ) {
    this.id_gdprRequest = id_gdprRequest;
    this.requestType = requestType;
    this.requestDate = requestDate;
    this.status = status;
    this.response = response;
    this.responseDate = responseDate;
    this.user = user;
  }
}
