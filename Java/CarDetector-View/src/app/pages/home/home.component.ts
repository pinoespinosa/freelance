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
  color = 'red';


  constructor(private service: Service) {
        Observable.interval(1000 * 1).subscribe(x => {
     this.updateColor();
    });
  }

  updateColor(){

  this.service.getColor().subscribe(
     cats => {
        console.log(cats);
       return this.color = cats;
     }
   );
    
  }

  getColor(){
    return this.color;
  }


}
 