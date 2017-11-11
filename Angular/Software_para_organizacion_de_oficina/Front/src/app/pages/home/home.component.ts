import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';

import { Client }                   from 'app/data-objects/cliente';
import { ClientFull }               from 'app/data-objects/clienteFull';

import { DialogComponent }               from 'app/pages/dialog.component';


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

  tipo_orden = 1;

  cliente: Client;
  cliente2 = ''


  items : ClientFull[];
  items_orig : ClientFull[];

  estados: string[] = ['Urgente', 'Asignado','Trajando','Listo por revisar','Avance Asesor Listo, Falta Hacer', 'Listo por pagar/enviar', 'Pendiente para trabajar'];

  showDialog = false;


  constructor(private service: Service) {
    this.getWorks();
    this.items=[];
    this.items_orig = [];
    this.cliente = new Client("","","", "","","", "","","",null);
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
            response => {

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

    getWorks(): void {

        let loading = this.service.getProducts().subscribe(
            response => {
                this.clientes = response;

                for (var aa of response){
                  for (var bb of aa.trabajos){
                    let fu = new ClientFull(aa.id,aa.nombre,"",bb.id,bb.tema,bb.titulo,bb.monto,bb.universidad,bb.fecha_entrega,bb.estado);
                    this.items.push(fu);
                    this.items_orig.push(fu);
                  }
                }

            }        
        );



    }
}
 