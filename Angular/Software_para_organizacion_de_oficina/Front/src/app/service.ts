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


  private server = 'http://18.216.175.95:8080/spring-security-rest/'

  constructor(private http: Http, private http2: HttpClient ) {
  }

  getProducts(): Observable<Client[]> {
//    return this.http.get("/assets/mock/fileFromExcel.json").map(this.extractData);
      return this.http.get(this.server+"/api/client/").map(this.extractData);
  }

  getClient(id): Observable<Client> {
      return this.http.get(this.server+"/api/client/" + id).map(this.extractData);
  }

  getTrabajo(idCliente, idTrabajo): Observable<Trabajo> {
      return this.http.get(this.server+"/api/" + idCliente + '/' + idTrabajo).map(this.extractData);
  }


  saveFile2(): Observable<Response> {
    return this.http.post("localhost:4000/saveFile","{}");
  }


  saveFile() {
    const headers = new HttpHeaders();
    this.http2.post('http://127.0.0.1:3000/saveFile', JSON.stringify(''), {
      headers: headers
    })
    .subscribe(data => {
      console.log(data);
    });
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
