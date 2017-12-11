import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';

import { Client }               from 'app/data-objects/cliente';
import { ClientFull }               from 'app/data-objects/clienteFull';

import { Service }   			      from 'app/service';
import {Observable} from 'rxjs/Rx';


@Component({
  selector: 'home',
  templateUrl: 'home.component.html',
  animations: [
  ],
})

export class HomeComponent   {

  title = 'app';
  color1 = 'red';
  color2 = 'red';
  cant1 = '0';
  cant2 = '0';
  direcciones = ["Esq Ejemplo 1 y Esq Ejemplo 2"]

  data = "";

  constructor(private service: Service) {
    Observable.interval(1000 * 1).subscribe(x => {
     this.updateColor();
    });

    Observable.interval(1000 * 10).subscribe(x => {
     this.getConfig();
            console.log(this.data)

    });

  this.service.getConfig().subscribe(
     cats => {
       this.data = cats;
     }
   );

  }


  getConfig(){

  this.service.getConfig().subscribe(
     cats => {
       return this.data = cats;
     }
   );
    
  }

  updateColor(){

  this.service.getColor1().subscribe(
     cats => {
       return this.color1 = cats;
     }
   );

  this.service.getColor2().subscribe(
     cats => {
       return this.color2 = cats;
     }
   );


  this.service.getCant1().subscribe(
     cats => {
       return this.cant1 = cats;
     }
   );

  this.service.getCant2().subscribe(
     cats => {
       return this.cant2 = cats;
     }
   );
    
  }

  getColor1(){
    return this.color1;
  }

  getColor2(){
    return this.color2;
  }

  getCant1(){
    return this.cant1;
  }

  getCant2(){
    return this.cant2;
  }

}
 