import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';

import { Client }               from 'app/data-objects/cliente';
import { Service }   			from 'app/service';


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


    constructor(private service: Service) {
    this.getWorks();

console.log(this.clientes)

    }

  sortNombre(){

  this.clientes.sort((a, b) => {
    if ( (a.nombre +a.apellido) < (b.nombre + b.apellido) ) return -1 * this.tipo_orden;
    else if ((a.nombre +a.apellido) > (b.nombre + b.apellido) ) return 1 * this.tipo_orden;
    else return 0;
  });

  this.tipo_orden = this.tipo_orden * -1
  
  }

  sortFecha(){

  this.clientes.sort((a, b) => {
    if ( (a.nombre +a.apellido) < (b.nombre + b.apellido) ) return -1 * this.tipo_orden;
    else if ((a.nombre +a.apellido) > (b.nombre + b.apellido) ) return 1 * this.tipo_orden;
    else return 0;
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



  }

    getWorks(): void {

        let loading = this.service.getProducts().subscribe(
            response => {
                this.clientes = response;
            }        );


    }
}
 