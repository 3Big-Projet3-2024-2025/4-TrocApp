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
