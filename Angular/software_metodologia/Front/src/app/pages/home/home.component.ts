import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';

import { Client }                   from 'app/data-objects/cliente';
import { ClientFull }               from 'app/data-objects/clienteFull';
import { Requerimiento }               from 'app/data-objects/requerimiento';


import { Service }   			          from 'app/service';


@Component({
  selector: 'home',
  templateUrl: 'home.component.html',
  animations: [
  ],
})

export class HomeComponent   {

  title = 'app';
  clientes : Client[];
  requerimientos: string[];

  showDialogAddReq = false;
  showDialogChangeDate = false;

  tipo_orden = 1;

  cliente: Client;
  cliente2: ClientFull;

  logo:string = "";


  items : ClientFull[];
  items_orig : ClientFull[];

  estados: string[] = ['Urgente', 'Asignado','Trabajando','Listo por revisar','Avance Asesor Listo', 'Listo por pagar/enviar', 'Pendiente para t/ luego'];

  esstado:string;


  showDialog = false;


  constructor(private service: Service, private router: Router,) {
    this.items=[];
    this.items_orig = [];
    this.cliente = new Client("","","", "","","", "","","",null);
    this.cliente2 = new ClientFull("","","","","","","","","","","","","","");
    this.logo = localStorage.getItem('logo');


  }

  callType(est){
    this.esstado = est;

  }


  addReq(req){

    let re = new Requerimiento(req,"","");

    let loading = this.service.agregarRequerimiento(this.cliente2.clienteID, this.cliente2.id_trabajo, re).subscribe(
      response =>{ 
        let aa = response;
      }         
    );
  }





  myFunction(valor){
    
    this.items = [];
    console.log(valor)
    for (var aa of this.items_orig){

      if ( aa.nombre.toLowerCase().includes(valor.toLowerCase()) ||  aa.tema.toLowerCase().includes(valor.toLowerCase()) ){
        this.items.push(aa);
      }
    } 
  }

  logout(){
    localStorage.clear();
    this.router.navigate(['/login'])    
  }



}
 