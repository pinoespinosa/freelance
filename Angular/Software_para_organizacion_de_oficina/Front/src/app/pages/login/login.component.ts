import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';
import { Service }   			      from 'app/service';
import { Client }               from 'app/data-objects/cliente';


@Component({
  selector: 'login',
  templateUrl: 'login.component.html',
  animations: [
  ],
})

export class LoginComponent implements OnInit  {
	usuario= '';
	password= '';
	constructor( private router: Router, private service: Service ) {

	};

	ngOnInit(): void {

	};

	do(){
		let loginServ = this.service.logIn(this.usuario, this.password).subscribe(
	      response =>{ 
	        let aa = response;
	        debugger;
	      }         
	    );
	};

	guardarUser(key, value){
		this[key] = value;
	};

}