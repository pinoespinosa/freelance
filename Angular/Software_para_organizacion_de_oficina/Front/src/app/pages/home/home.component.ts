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

  tipo_orden = 1;

  cliente: Client;
  cliente2: ClientFull;



  items : ClientFull[];
  items_orig : ClientFull[];

  estados: string[] = ['Urgente', 'Asignado','Trabajando','Listo por revisar','Avance Asesor Listo', 'Listo por pagar/enviar', 'Pendiente para t/ luego'];

  esstado:string;


  showDialog = false;


  constructor(private service: Service) {
    this.getWorks();
    this.items=[];
    this.items_orig = [];
    this.cliente = new Client("","","", "","","", "","","",null);
    this.cliente2 = new ClientFull("","","","","","","","","","","");

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



  sortNombre(){
    this.items.sort((a, b) => {
      if ( (a.nombre +a.apellido) < (b.nombre + b.apellido) ) 
        return -1 * this.tipo_orden;
      else 
        if ((a.nombre +a.apellido) > (b.nombre + b.apellido) ) 
          return 1 * this.tipo_orden;
        else 
          return 0;
    });
    this.tipo_orden = this.tipo_orden * -1
  }

  sortFecha(){
    this.items.sort((a, b) => {
      if ( (a.entrega) < (b.entrega) )
        return -1 * this.tipo_orden;
      else 
        if ((a.entrega) > (b.entrega) ) 
          return 1 * this.tipo_orden;
        else 
          return 0;
    });
    this.tipo_orden = this.tipo_orden * -1 
  }

  sortEstado(){

  console.log("Imprimo estado")

    this.items.sort((a, b) => {
      if ( (a.estado) < (b.estado) )
        return -1 * this.tipo_orden;
      else 
        if ((a.estado) > (b.estado) ) 
          return 1 * this.tipo_orden;
        else 
          return 0;
    });
    this.tipo_orden = this.tipo_orden * -1 
    console.log(this.items)
  }

  sortUniversidad(){
    this.items.sort((a, b) => {
      if ( (a.universidad) < (b.universidad) )
        return -1 * this.tipo_orden;
      else 
        if ((a.universidad) > (b.universidad) ) 
          return 1 * this.tipo_orden;
        else 
          return 0;
    });
    this.tipo_orden = this.tipo_orden * -1 
  }


  changeState(cliente, trabajo, estado){
    

        let loading = this.service.getChangeStateTrabajo(cliente, trabajo, estado).subscribe(
            response =>{ 
              let aa = response;

              this.getWorks();
            }         
        );
        alert("Se ha actualizado el estado.")


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

    getWorks(): void {

    console.log("update front")
    this.items=[];
    this.items_orig = [];
    this.cliente2 = null;

        let loading = this.service.getProducts().subscribe(
            response => {
                this.clientes = response;

                for (var aa of response){
                  for (var bb of aa.trabajos){
                    let fu = new ClientFull(aa.id,aa.nombre,"",bb.id,bb.tema,bb.titulo,bb.monto,bb.saldo,bb.universidad,bb.fecha_entrega,bb.estado);
                    this.items.push(fu);
                    this.items_orig.push(fu);
                  }
                }

            }        
        );



    }
}
 