// Observable Version
import { Injectable }               from '@angular/core';
import { Http, Headers, Response, RequestOptions, ResponseContentType }        from '@angular/http';

import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable }               from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { Client }                 from 'app/data-objects/cliente';
import { Trabajo }                from 'app/data-objects/trabajo';
import { Login }                  from 'app/data-objects/login';
import { Auditoria }              from 'app/data-objects/auditoria';
import { Asesor }                 from 'app/data-objects/asesor';


@Injectable()
export class Service {


  private server = 'http://18.216.175.95:8080/spring-security-rest/'
  //private server = 'http://192.168.1.4:8080/officemanager/'
  //private server = 'http://localhost:8080/officemanager/'

  getServer(): string{
    return this.server;
  }

  constructor(private http: Http, private http2: HttpClient ) {
  }

  logIn(user, pass): Observable<Login> {
    return this.http.get(this.server+"api/auth?user="+user+"&pass="+pass).map(this.extractData);
  }
  createUser(user, pass, rol, token): Observable<Login> {
    return this.http.get(this.server+"api/createUser?user="+user+"&pass="+pass+"&rol="+rol+"&token="+token).map(this.extractData);
  }

  getPendingJobs(): Observable<Client[]> {
    return this.http.get(this.server+"api/client/pending").map(this.extractData);
  }  

  getSalesGraphNewClientsCash(cantidadDias, cantidadValores): Observable<Number[]> {
    return this.http.get(this.server+"api/client/sells/period/chash/newClients?cantidadDias="+cantidadDias+"&cantidadValores="+cantidadValores).map(this.extractData);
  }  

  getSalesGraphOldClientsCash(cantidadDias, cantidadValores): Observable<Number[]> {
    return this.http.get(this.server+"api/client/sells/period/chash/oldClients?cantidadDias="+cantidadDias+"&cantidadValores="+cantidadValores).map(this.extractData);
  }  

  getSalesGraphNewClientsAmmount(cantidadDias, cantidadValores): Observable<Number[]> {
    return this.http.get(this.server+"api/client/sells/period/amount/newClients?cantidadDias="+cantidadDias+"&cantidadValores="+cantidadValores).map(this.extractData);
  }  

  getClientsFiltred(fechaDesde, fechaHasta): Observable<Client[]> {
      return this.http.get(this.server+"api/client/filtredPagos?fechaDesde="+fechaDesde+"&fechaHasta="+fechaHasta).map(this.extractData);
  }

  getProducts(): Observable<Client[]> {
      return this.http.get(this.server+"api/client/").map(this.extractData);
  }

  getClient(id): Observable<Client> {
      return this.http.get(this.server+"api/client/" + id).map(this.extractData);
  }


// ######################################  ASESORES  ######################################

  createAsesor(asesor): Observable<Asesor> {
      var headers = new Headers();
      headers.append('acces-control-allow-origin','*');
      headers.append('acces-token', localStorage.getItem('token'));
      return this.http.post(this.server + 'api/asesor/create',asesor, { headers: headers }).map(this.extractData);
  }

  getAsesor(): Observable<Asesor[]> {
    var headers = new Headers();
    headers.append('acces-control-allow-origin','*');
    headers.append('acces-token', localStorage.getItem('token'));
    return this.http.get(this.server+"api/asesor", { headers: headers }).map(this.extractData);
  }


// ######################################  CLIENTES  ######################################

  createCliente(client): Observable<Client> {
      var headers = new Headers();
      headers.append('acces-control-allow-origin','*');
      headers.append('acces-token', localStorage.getItem('token'));
      return this.http.post(this.server + 'api/client/create',client, { headers: headers }).map(this.extractData);
  }

  editCliente(client): Observable<Client> {
      var headers = new Headers();
      headers.append('acces-control-allow-origin','*');
      headers.append('acces-token', localStorage.getItem('token'));
      return this.http.post(this.server + 'api/client/edit',client, { headers: headers }).map(this.extractData);
  }

  editTrabajo(idCliente, trabajo): Observable<Client> {
      var headers = new Headers();
      headers.append('acces-control-allow-origin','*');
      headers.append('acces-token', localStorage.getItem('token'));
      return this.http.post(this.server + 'api/'+idCliente+'/trabajo/edit',trabajo, { headers: headers }).map(this.extractData);
  }

  editAsesor(idCliente, idTrabajo,asesor): Observable<Client> {
      var headers = new Headers();
      headers.append('acces-control-allow-origin','*');
      headers.append('acces-token', localStorage.getItem('token'));
      return this.http.post(this.server + 'api/'+idCliente+'/'+idTrabajo+'/asesor?asesor='+asesor, { headers: headers }).map(this.extractData);
  }

  crearTrabajo(idCliente, trabajo): Observable<Client> {
      var headers = new Headers();
      headers.append('acces-control-allow-origin','*');
      headers.append('acces-token', localStorage.getItem('token'));
      return this.http.post(this.server + 'api/'+idCliente+'/trabajo',trabajo, { headers: headers }).map(this.extractData);
  }

  crearPago(idCliente, idTrabajo, pago): Observable<Client> {
      var headers = new Headers();
      headers.append('acces-control-allow-origin','*');
      headers.append('acces-token', localStorage.getItem('token'));
      return this.http.post(this.server + 'api/'+idCliente+'/'+idTrabajo+'/pago',pago, { headers: headers }).map(this.extractData);
  }

  crearUniversidad(universidad): Observable<String> {
      var headers = new Headers();
      headers.append('acces-control-allow-origin','*');
      headers.append('acces-token', localStorage.getItem('token'));
      return this.http.post(this.server + 'api/universidad',universidad, { headers: headers }).map(this.extractData);
  }

  crearCarrera(carrera): Observable<String> {
      var headers = new Headers();
      headers.append('acces-control-allow-origin','*');
      headers.append('acces-token', localStorage.getItem('token'));
      return this.http.post(this.server + 'api/carrera',carrera, { headers: headers }).map(this.extractData);
  }

  crearDondeEntero(dondeEntero): Observable<String> {
      var headers = new Headers();
      headers.append('acces-control-allow-origin','*');
      headers.append('acces-token', localStorage.getItem('token'));
      return this.http.post(this.server + 'api/dondeEntero',dondeEntero, { headers: headers }).map(this.extractData);
  }

  agregarRequerimiento(idCliente, idTrabajo, requerimiento){
      var headers = new Headers();
      headers.append('acces-control-allow-origin','*');
      headers.append('acces-token', localStorage.getItem('token'));
      return this.http.post(this.server+"api/" + idCliente + '/' + idTrabajo + '/requerimiento' ,requerimiento, { headers: headers }).map(this.extractData);
  }

  getAudit(): Observable<Auditoria[]> {
    var headers = new Headers();
    headers.append('acces-control-allow-origin','*');
    headers.append('acces-token', localStorage.getItem('token'));
    return this.http.get(this.server+"api/auditoria", { headers: headers }).map(this.extractData);
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
      headers.append('acces-control-allow-origin','*');
      headers.append('acces-token', localStorage.getItem('token')); 
      return this.http.post(this.server+"/api/" + idCliente + '/' + idTrabajo +'/status?estado='+estado , '', { headers: headers }).map(this.extractData);
  }

  updateFechaEntrega(idCliente, idTrabajo, fechaNueva): Observable<Trabajo> {
      var headers = new Headers();
      headers.append('acces-control-allow-origin','*');
      headers.append('acces-token', localStorage.getItem('token'));
      return this.http.post(this.server+"api/" + idCliente + '/' + idTrabajo +'/fechaEntrega?fechaNueva='+fechaNueva , '', { headers: headers }).map(this.extractData);
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
