import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';

import { Client }               from 'app/data-objects/cliente';
import { ClientFull }               from 'app/data-objects/clienteFull';

import { Service }   			      from 'app/service';


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

  items : ClientFull[];


  constructor(private service: Service) {
    this.getWorks();
    this.items=[];
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



  myFunction(valor){
       
    let loading = this.service.getProducts().subscribe(
    response => {
      this.clientes = [];
        for (var aa of response){
          if ( (aa.nombre.toLowerCase().includes(valor.toLowerCase())) || (aa.apellido.toLowerCase().includes(valor.toLowerCase()) )){
            this.clientes.push(aa);
            console.log(aa.nombre)

          }
        }
    }        
  );

 console.log(this.clientes)
   
  this.service.saveFile();

  }

    getWorks(): void {

        let loading = this.service.getProducts().subscribe(
            response => {
                this.clientes = response;

                for (var aa of response){
                  for (var bb of aa.trabajos){
                    let fu = new ClientFull(aa.id,aa.nombre,aa.apellido,bb.id,bb.tema,bb.titulo,bb.monto,bb.universidad,bb.entrega,bb.estado);
                    this.items.push(fu);
                  }
                }

            }        
        );



    }
}
 