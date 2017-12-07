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


  constructor(private service: Service) {
        Observable.interval(1000 * 1).subscribe(x => {
     this.updateColor();
    });
  }

  updateColor(){

  this.service.getColor1().subscribe(
     cats => {
        console.log(cats);
       return this.color1 = cats;
     }
   );

  this.service.getColor2().subscribe(
     cats => {
        console.log(cats);
       return this.color2 = cats;
     }
   );

    
  }

  getColor1(){
    return this.color1;
  }

  getColor2(){
    return this.color2;
  }

}
 