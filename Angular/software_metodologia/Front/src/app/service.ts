// Observable Version

import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { Http, Headers, Response, RequestOptions, ResponseContentType }        from '@angular/http';
import { Injectable }                                   from '@angular/core';
import { HttpClient, HttpHeaders }                      from '@angular/common/http';
import { Observable }                                   from 'rxjs/Observable';

import { Client }                                       from 'app/data-objects/cliente';
import { Trabajo }                                      from 'app/data-objects/trabajo';
import { Login }                                        from 'app/data-objects/login';
import { CuerpoColegiado }                              from 'app/data-objects/cuerpoColegiado';
import { CuerpoColegiadoSelect }                        from 'app/data-objects/cuerpoColegiadoSelect';

import { Acta }                                         from 'app/data-objects/acta';
import { Usuario }                                      from 'app/data-objects/usuario';
import { Tema }                                         from 'app/data-objects/tema';
import { Tarea }                                        from 'app/data-objects/tarea';


@Injectable()
export class Service {

private serverAWS = 'http://ec2-52-67-241-219.sa-east-1.compute.amazonaws.com:8080/metodologia-manager/'
//private serverAWS = 'http://192.168.1.4:8080/metodologiamanager/'
//private serverAWS = 'http://localhost:8080/metodologiamanager/'
//private serverAWS = 'http://192.168.1.4:8080/metodologiamanager/'

constructor(private http: Http, private http2: HttpClient ) {

}

getServer():string{
  
  let serverIP= localStorage.getItem('serverIP');

  if (serverIP && serverIP != null && serverIP != 'null')
    return serverIP;
  else
    return this.serverAWS;
}


validateImage(file: File): Observable<any> {
    let headers = new Headers({'acces-token' : localStorage.getItem('token')});
    let formData: FormData = new FormData();
    formData.append("file", file);
    let options = new RequestOptions({headers: headers});
    return this.http.post(this.getServer()+"api/images", formData, options)
                    .map(this.extractData)
                    .catch(this.handleError);
}

logIn(user, pass): Observable<Login> {
  return this.http.get(this.getServer()+"api/auth?email="+user+"&pass="+pass).map(this.extractData);
}

logInSuper(user, pass, empresa): Observable<Login> {
  return this.http.get(this.getServer()+"api/auth/superuser?email="+user+"&pass="+pass+'&empresaID='+empresa).map(this.extractData);
}

changePasswordUsuario(pass, passNueva, passConfirm): Observable<Login> {
  var headers = new Headers();
  headers.append('acces-token', localStorage.getItem('token'));
  return this.http.post(this.getServer()+"api/changePassword?pass=" + pass + '&passNueva='+ passNueva + '&passConfirm=' + passConfirm, pass, { headers: headers }).map(this.extractData);
}


getCuerpoColegiados(): Observable<CuerpoColegiado[]> {
  var headers = new Headers();
  headers.append('acces-token', localStorage.getItem('token'));
  return this.http.get(this.getServer()+"api/cuerpocolegiado", { headers: headers }).map(this.extractData);
}

getCuerpoColegiadosSimple(empresaID): Observable<CuerpoColegiado[]> {
  var headers = new Headers();
  headers.append('acces-token', localStorage.getItem('token'));
  return this.http.get(this.getServer()+"api/cuerpocolegiado/lista?empresaID="+empresaID, { headers: headers }).map(this.extractData);
}

getCuerpoColegiado(cuerpocolegiadoID): Observable<CuerpoColegiado> {
  return this.http.get(this.getServer()+"api/cuerpocolegiado/"+cuerpocolegiadoID).map(this.extractData);
}

getUsuarios(cuerpoColegiado): Observable<Usuario[]> {
  var headers = new Headers();
  headers.append('acces-token', localStorage.getItem('token'));
  return this.http.get(this.getServer()+"api/usuario?cuerpoColegiadoID=" +cuerpoColegiado, { headers: headers }).map(this.extractData);
}

getUsuariosConActa(acta): Observable<Usuario[]> {
  var headers = new Headers();
  headers.append('acces-token', localStorage.getItem('token'));
  return this.http.get(this.getServer()+"api/acta/usuario?actaID=" +acta, { headers: headers }).map(this.extractData);
}

getEmpresas(): Observable<any[]> {
  var headers = new Headers();
  headers.append('acces-token', localStorage.getItem('token'));
  return this.http.get(this.getServer()+"api/empresa/list", { headers: headers }).map(this.extractData);
}

updateEmpresa(empresa): Observable<any> {
  var headers = new Headers();
  headers.append('acces-token', localStorage.getItem('token'));
  return this.http.post(this.getServer()+"api/empresa/update", empresa, { headers: headers }).map(this.extractData);
}

createEmpresa(empresa): Observable<any> {
  var headers = new Headers();
  headers.append('acces-token', localStorage.getItem('token'));
  return this.http.post(this.getServer()+"api/empresa/create", empresa, { headers: headers }).map(this.extractData);
}

createCuerpo(cuerpo, empresaID): Observable<any> {
  var headers = new Headers();
  headers.append('acces-token', localStorage.getItem('token'));
  return this.http.post(this.getServer()+"api/cuerpocolegiado/create?empresaID="+empresaID, cuerpo, { headers: headers }).map(this.extractData);
}

editCuerpo(cuerpo): Observable<any> {
  var headers = new Headers();
  headers.append('acces-token', localStorage.getItem('token'));
  return this.http.post(this.getServer()+"api/cuerpocolegiado/edit", cuerpo, { headers: headers }).map(this.extractData);
}

getLastActa(cuerpoColegiadoID): Observable<Acta> {
  var headers = new Headers();
  headers.append('acces-token', localStorage.getItem('token'));
  return this.http.get(this.getServer() + 'api/'+ cuerpoColegiadoID + '/acta/last', { headers: headers }).map(this.extractData);
}

getTemasConsulta(cuerpoColegiadoID,actaID): Observable<Tema[]> {
  var headers = new Headers();
  headers.append('acces-token', localStorage.getItem('token'));
  return this.http.get(this.getServer() + 'api/'+ cuerpoColegiadoID + '/tema/historico?actaID='+actaID, { headers: headers }).map(this.extractData);
}

getTemasConsulta2(actaID): Observable<Tema[]> {
  var headers = new Headers();
  headers.append('acces-token', localStorage.getItem('token'));
  return this.http.get(this.getServer() + 'api/tema/historico2?actaID='+actaID, { headers: headers }).map(this.extractData);
}

getActasCitadas(): Observable<Acta[]> {
  var headers = new Headers();
  headers.append('acces-token', localStorage.getItem('token'));
  return this.http.get(this.getServer() + 'api/acta/citada', { headers: headers }).map(this.extractData);
}

getActasFinMente(t): Observable<Acta[]> {
  var headers = new Headers();
  headers.append('acces-token', localStorage.getItem('token'));
  return this.http.get(this.getServer() + 'api/acta/filtroMente?texto='+t, { headers: headers }).map(this.extractData);
}

getActa(actaID): Observable<Acta> {
  var headers = new Headers();
  headers.append('acces-token', localStorage.getItem('token'));
  return this.http.get(this.getServer() + 'api/acta/'+ actaID, { headers: headers }).map(this.extractData);
}

getTemas(actaID): Observable<Tema[]> {
  var headers = new Headers();
  headers.append('acces-token', localStorage.getItem('token'));
  return this.http.get(this.getServer() + 'api/'+ actaID + '/acta/tema/abierto', { headers: headers }).map(this.extractData);
}

createUser(nombre, email, listaCC): Observable<Usuario> {
  var headers = new Headers();
  headers.append('acces-token', localStorage.getItem('token'));
  return this.http.post(this.getServer() + '/api/createUser?nombre='+nombre+'&email='+email+'&pass=1234&logo=dd&rol=SOLO_CONSULTA'+listaCC,'', { headers: headers }).map(this.extractData);
}

createUserAdmin(nombre, email, listaCC, empresaID, perfil): Observable<Usuario> {
  var headers = new Headers();
  headers.append('acces-token', localStorage.getItem('token'));
  return this.http.post(this.getServer() + '/api/createUser/admin?empresaID='+empresaID+'&nombre='+nombre+'&email='+email+'&pass=1234&logo=dd&rol='+perfil+ listaCC,'', { headers: headers }).map(this.extractData);
}

createActa(cuerpoColegiadoID, acta): Observable<Acta> {
  var headers = new Headers();
  headers.append('acces-token', localStorage.getItem('token'));
  return this.http.post(this.getServer() + 'api/'+ cuerpoColegiadoID + '/acta/create',acta, { headers: headers }).map(this.extractData);
}

createTema(cuerpoColegiadoID, tema, actaID, cuerpoColList, comm): Observable<Tema> {
  var headers = new Headers();
  headers.append('acces-token', localStorage.getItem('token'));
  return this.http.post(this.getServer() + 'api/'+ cuerpoColegiadoID + '/tema/create?actaID='+actaID + '&cuerpoColList=' + cuerpoColList + '&comm=' + comm,tema, { headers: headers }).map(this.extractData);
}

editActa(cuerpoColegiadoID, acta): Observable<Acta> {

  var headers = new Headers();
  return this.http.post(this.getServer() + 'api/'+ cuerpoColegiadoID + '/acta/edit',acta, { headers: headers }).map(this.extractData);
}

createComentario(cuerpoColegiadoID, tema, com): Observable<Tema> {
  var headers = new Headers();
  headers.append('acces-token', localStorage.getItem('token'));
  return this.http.post(this.getServer() + 'api/'+ cuerpoColegiadoID + '/tema/addComment?temaID='+tema+'&comentario='+com, tema,{ headers: headers }).map(this.extractData);
}

createComentarioTarea(cuerpoColegiadoID, tema, tarea, com): Observable<Tarea> {
  var headers = new Headers();
  headers.append('acces-token', localStorage.getItem('token'));
  return this.http.post(this.getServer() + 'api/'+ cuerpoColegiadoID + '/tarea/addComment?temaID='+tema + '&tareaID='+tarea +'&comentario='+com, tema,{ headers: headers }).map(this.extractData);
}

closeTarea(cuerpoColegiadoID, tema, tarea, com): Observable<Tarea> {
  var headers = new Headers();
  headers.append('acces-token', localStorage.getItem('token'));
  return this.http.post(this.getServer() + 'api/'+ cuerpoColegiadoID + '/tarea/close?temaID='+tema + '&tareaID='+tarea + '&comentario='+com, tema,{ headers: headers }).map(this.extractData);
}

crearTarea(cuerpoColegiadoID, temaID, tarea): Observable<Tema> {
  var headers = new Headers();
  headers.append('acces-token', localStorage.getItem('token'));
  return this.http.post(this.getServer() + 'api/'+ cuerpoColegiadoID + '/tarea/create?temaID='+temaID,tarea, { headers: headers }).map(this.extractData);
}



closeTema(cuerpoColegiadoID, tema, com): Observable<Tema> {
  var headers = new Headers();
  headers.append('acces-token', localStorage.getItem('token'));
  return this.http.post(this.getServer() + 'api/'+ cuerpoColegiadoID + '/tema/close?temaID='+tema+'&comentario='+com, tema,{ headers: headers }).map(this.extractData);
}

checkAvanzarTareas(actaID): Observable<Tema> {
  var headers = new Headers();
  headers.append('acces-token', localStorage.getItem('token'));
  return this.http.get(this.getServer() + 'api/'+ actaID + '/acta/isDone', { headers: headers }).map(this.extractData);  }

checkAvanzarTareasOK(actaID): Observable<Tema> {
  var headers = new Headers();
  headers.append('acces-token', localStorage.getItem('token'));
  return this.http.get(this.getServer() + 'api/'+ actaID + '/acta/isDone/ok', { headers: headers }).map(this.extractData);  }



updateActaPaso(cuerpoColegiadoID, actaID, paso): Observable<Acta> {
  var headers = new Headers();
  headers.append('acces-token', localStorage.getItem('token'));
  return this.http.post(this.getServer() + 'api/'+ cuerpoColegiadoID + '/' + actaID + '/updatePaso?paso='+paso, paso ,{ headers: headers }).map(this.extractData);}

closeActa(cuerpoColegiadoID, actaID, acta): Observable<Acta> {
  var headers = new Headers();
  headers.append('acces-token', localStorage.getItem('token'));
  return this.http.post(this.getServer() + 'api/'+ cuerpoColegiadoID + '/' + actaID + '/close', acta ,{ headers: headers }).map(this.extractData); }

updateActaIntegrantes(cuerpoColegiadoID, actaID, acta): Observable<Acta> {
  var headers = new Headers();
  headers.append('acces-token', localStorage.getItem('token'));
  return this.http.post(this.getServer() + 'api/'+ cuerpoColegiadoID + '/' + actaID + '/updateIntegrantes', acta ,{ headers: headers }).map(this.extractData); }

    getOtrosCuerposColegiado(cuerpoColegiadoID): Observable<CuerpoColegiadoSelect[]> {
      var headers = new Headers();
      headers.append('acces-token', localStorage.getItem('token'));
      return this.http.get(this.getServer() + 'api/cuerpocolegiado/'+ cuerpoColegiadoID + '/otros',{ headers: headers }).map(this.extractData);
    }





    getPendingJobs(): Observable<Client[]> {
      return this.http.get(this.getServer()+"api/client/pending").map(this.extractData);
    }  

    getSalesGraphNewClientsCash(cantidadDias, cantidadValores): Observable<Number[]> {
      return this.http.get(this.getServer()+"api/client/sells/period/chash/newClients?cantidadDias="+cantidadDias+"&cantidadValores="+cantidadValores).map(this.extractData);
    }  

    getSalesGraphOldClientsCash(cantidadDias, cantidadValores): Observable<Number[]> {
      return this.http.get(this.getServer()+"api/client/sells/period/chash/oldClients?cantidadDias="+cantidadDias+"&cantidadValores="+cantidadValores).map(this.extractData);
    }  

    getSalesGraphNewClientsAmmount(cantidadDias, cantidadValores): Observable<Number[]> {
      return this.http.get(this.getServer()+"api/client/sells/period/amount/newClients?cantidadDias="+cantidadDias+"&cantidadValores="+cantidadValores).map(this.extractData);
    }  

    getClientsFiltred(fechaDesde, fechaHasta): Observable<Client[]> {
      return this.http.get(this.getServer()+"api/client/filtredPagos?fechaDesde="+fechaDesde+"&fechaHasta="+fechaHasta).map(this.extractData);
    }

    getProducts(): Observable<Client[]> {
      return this.http.get(this.getServer()+"api/client/").map(this.extractData);
    }

    getClient(id): Observable<Client> {
      return this.http.get(this.getServer()+"api/client/" + id).map(this.extractData);
    }

    createCliente(client): Observable<Client> {
      var headers = new Headers();
      headers.append('acces-control-allow-origin','*');
      headers.append('acces-token', localStorage.getItem('token'));
      return this.http.post(this.getServer() + 'api/client/create',client, { headers: headers }).map(this.extractData);
    }

    editCliente(client): Observable<Client> {
      var headers = new Headers();
      headers.append('acces-control-allow-origin','*');
      headers.append('acces-token', localStorage.getItem('token'));
      return this.http.post(this.getServer() + 'api/client/edit',client, { headers: headers }).map(this.extractData);
    }

    editTrabajo(idCliente, trabajo): Observable<Client> {
      var headers = new Headers();
      headers.append('acces-control-allow-origin','*');
      headers.append('acces-token', localStorage.getItem('token'));
      return this.http.post(this.getServer() + 'api/'+idCliente+'/trabajo/edit',trabajo, { headers: headers }).map(this.extractData);
    }

    editAsesor(idCliente, idTrabajo,asesor): Observable<Client> {
      var headers = new Headers();
      headers.append('acces-control-allow-origin','*');
      headers.append('acces-token', localStorage.getItem('token'));
      return this.http.post(this.getServer() + 'api/'+idCliente+'/'+idTrabajo+'/asesor?asesor='+asesor, { headers: headers }).map(this.extractData);
      }

      crearTrabajo(idCliente, trabajo): Observable<Client> {
      var headers = new Headers();
      headers.append('acces-control-allow-origin','*');
      headers.append('acces-token', localStorage.getItem('token'));
      return this.http.post(this.getServer() + 'api/'+idCliente+'/trabajo',trabajo, { headers: headers }).map(this.extractData);
      }

      crearPago(idCliente, idTrabajo, pago): Observable<Client> {
      var headers = new Headers();
      headers.append('acces-control-allow-origin','*');
      headers.append('acces-token', localStorage.getItem('token'));
      return this.http.post(this.getServer() + 'api/'+idCliente+'/'+idTrabajo+'/pago',pago, { headers: headers }).map(this.extractData);
      }

      crearUniversidad(universidad): Observable<String> {
      var headers = new Headers();
      headers.append('acces-control-allow-origin','*');
      headers.append('acces-token', localStorage.getItem('token'));
      return this.http.post(this.getServer() + 'api/universidad',universidad, { headers: headers }).map(this.extractData);
      }

      crearCarrera(carrera): Observable<String> {
      var headers = new Headers();
      headers.append('acces-control-allow-origin','*');
      headers.append('acces-token', localStorage.getItem('token'));
      return this.http.post(this.getServer() + 'api/carrera',carrera, { headers: headers }).map(this.extractData);
      }

      crearDondeEntero(dondeEntero): Observable<String> {
      var headers = new Headers();
      headers.append('acces-control-allow-origin','*');
      headers.append('acces-token', localStorage.getItem('token'));
      return this.http.post(this.getServer() + 'api/dondeEntero',dondeEntero, { headers: headers }).map(this.extractData);
      }

      agregarRequerimiento(idCliente, idTrabajo, requerimiento){
      var headers = new Headers();
      headers.append('acces-control-allow-origin','*');
      headers.append('acces-token', localStorage.getItem('token'));
      return this.http.post(this.getServer()+"api/" + idCliente + '/' + idTrabajo + '/requerimiento' ,requerimiento, { headers: headers }).map(this.extractData);
      }



      getUniversidades(): Observable<string[]> {
      return this.http.get(this.getServer()+"api/universidad").map(this.extractData);
      }

      getCarreras(): Observable<string[]> {
      return this.http.get(this.getServer()+"api/carrera").map(this.extractData);
      }

      getDondeSeEntero(): Observable<string[]> {
      return this.http.get(this.getServer()+"api/dondeEntero").map(this.extractData);
      }

      getTrabajo(idCliente, idTrabajo): Observable<Trabajo> {
      return this.http.get(this.getServer()+"/api/" + idCliente + '/' + idTrabajo).map(this.extractData);
      }

      getChangeStateTrabajo(idCliente, idTrabajo, estado): Observable<Trabajo> {
      var headers = new Headers();
      headers.append('acces-control-allow-origin','*');
      headers.append('acces-token', localStorage.getItem('token')); 
      return this.http.post(this.getServer()+"/api/" + idCliente + '/' + idTrabajo +'/status?estado='+estado , '', { headers: headers }).map(this.extractData);
      }

      updateFechaEntrega(idCliente, idTrabajo, fechaNueva): Observable<Trabajo> {
      var headers = new Headers();
      headers.append('acces-control-allow-origin','*');
      headers.append('acces-token', localStorage.getItem('token'));
      return this.http.post(this.getServer()+"api/" + idCliente + '/' + idTrabajo +'/fechaEntrega?fechaNueva='+fechaNueva , '', { headers: headers }).map(this.extractData);
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
