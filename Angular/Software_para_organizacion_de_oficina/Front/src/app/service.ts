// Observable Version
import { Injectable }               from '@angular/core';
import { Http, Headers, Response, RequestOptions, ResponseContentType }        from '@angular/http';

import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable }               from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { Client }               from 'app/data-objects/cliente';
import { Trabajo }               from 'app/data-objects/trabajo';


@Injectable()
export class Service {


  //private server = 'http://18.216.175.95:8080/spring-security-rest/'
  private server = 'http://192.168.1.4:8080/officemanager/'

  constructor(private http: Http, private http2: HttpClient ) {
  }

  getProducts(): Observable<Client[]> {
      return this.http.get(this.server+"api/client/").map(this.extractData);
  }

  getClient(id): Observable<Client> {
      return this.http.get(this.server+"api/client/" + id).map(this.extractData);
  }

  createCliente(client): Observable<Client> {
      var headers = new Headers();
      headers.append('acces-control-allow-origin','*')      
      return this.http.post(this.server + 'api/client/create',client, { headers: headers }).map(this.extractData);
  }

  crearTrabajo(idCliente, empresa): Observable<Client> {
      var headers = new Headers();
      headers.append('acces-control-allow-origin','*')      
      return this.http.post(this.server + 'api/'+idCliente+'/create',empresa, { headers: headers }).map(this.extractData);
  }

  getUniversidades(): Observable<string[]> {
      return this.http.get(this.server+"api/universidad").map(this.extractData);
  }

  getCarreras(): Observable<string[]> {
      return this.http.get(this.server+"api/carrera").map(this.extractData);
  }

  getDondeSeEntero(): Observable<string[]> {
      return this.http.get(this.server+"api/dondeEntero").map(this.extractData);
  }

  getTrabajo(idCliente, idTrabajo): Observable<Trabajo> {
      return this.http.get(this.server+"/api/" + idCliente + '/' + idTrabajo).map(this.extractData);
  }


  getChangeStateTrabajo(idCliente, idTrabajo, estado): Observable<Trabajo> {
      var headers = new Headers();
      headers.append('acces-control-allow-origin','*')      
      return this.http.post(this.server+"/api/" + idCliente + '/' + idTrabajo +'/status?estado='+estado , '', { headers: headers }).map(this.extractData);
  }


  saveFile2(): Observable<Response> {
    return this.http.post("localhost:4000/saveFile","{}");
  }


  private extractData(res: Response) {

      let body = res.json();
   return body || { };
  }


  private handleError (error: Response | any) {
    // In a real world app, you might use a remote logging infrastructure
    let errMsg: string;
    if (error instanceof Response) {
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    console.error(errMsg);
    return Observable.throw(errMsg);
  }
}
