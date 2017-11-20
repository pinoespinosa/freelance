import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';
import { Service }   			      from 'app/service';
import { Client }               from 'app/data-objects/cliente';
import { Login }               from 'app/data-objects/login';


@Component({
  selector: 'register-user',
  templateUrl: 'register-user.component.html',
  animations: [
  ],
})

export class RegisterUserComponent implements OnInit  {
	usuario= '';
	password= '';
	rol= '';
	constructor( private router: Router, private service: Service ) {

	};

	ngOnInit(): void {

	};

	do(){
		let token = localStorage.getItem('token');
		this.service.createUser(this.usuario, this.password, this.rol, token).subscribe(
	      response =>{ 
            const a = response;
            alert('Usuario creado exitosamente');
	      }         
	    );
	};

	guardarUser(key, value){
		this[key] = value;
	};

}