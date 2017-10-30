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


    constructor(private service: Service) {
    this.getWorks();

console.log(this.clientes)

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
 