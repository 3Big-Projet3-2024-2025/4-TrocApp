/*import { User } from './user.model'; // Assuming this is the correct path for the User model

export class GdprRequest {
  id_gdprRequest: number;
  requesttype: string;
  requestdate: string; // Date formatted as string
  status: string;
  response: string;
  responsedate: string; // Date formatted as string
  consent: boolean;
  justification: string;  // Nouvelle propriété ajoutée
  user: {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    actif: boolean;
};

  constructor(
    id_gdprRequest: number,
    requesttype: string,
    requestdate: string,
    status: string,
    response: string,
    responsedate: string,
    consent: boolean,
    justification: string,  // Ajout dans le constructeur
    user: User
  ) {
    this.id_gdprRequest = id_gdprRequest;
    this.requesttype = requesttype;
    this.requestdate = requestdate;
    this.status = status;
    this.response = response;
    this.responsedate = responsedate;
    this.consent = consent;
    this.justification = justification;  // Initialisation de la nouvelle propriété
    this.user = user;
  }
}*/

export class GdprRequest {
  id_gdprRequest: number;
  requesttype: string;
  requestdate: string;
  status: string;
  response: string;
  responsedate: string;
  consent: boolean;
  justification: string;

  user: {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    actif: boolean;
  };

  constructor(
    id_gdprRequest: number,
    requesttype: string,
    requestdate: string,
    status: string,
    response: string,
    responsedate: string,
    consent: boolean,
    justification: string,
    user: { id: number; firstName: string; lastName: string; email: string; actif: boolean }
  ) {
    this.id_gdprRequest = id_gdprRequest;
    this.requesttype = requesttype;
    this.requestdate = requestdate;
    this.status = status;
    this.response = response;
    this.responsedate = responsedate;
    this.consent = consent;
    this.justification = justification;
    this.user = user;
  }
}
