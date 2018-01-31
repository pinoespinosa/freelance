import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';
import { Service }   			      from 'app/service';
import { Client }               from 'app/data-objects/cliente';
import { Login }               from 'app/data-objects/login';


@Component({
  selector: 'login',
  templateUrl: 'login.component.html',
  animations: [
  ],
})

export class LoginComponent implements OnInit  {
	
	usuario= '';
	password= '';
	empresas = [];
	empresaSelect = null

	constructor( private router: Router, private service: Service ) {

	};

	ngOnInit(): void {

		this.service.getEmpresas().subscribe(
	      response =>{ 
	      		this.empresas = response;        
	      }         
	    );

	};


  selectCuerpo(cuerpo):void{
    this.empresaSelect = this.empresas[cuerpo.selectedIndex-1];
  }

	do(){

		let loginServ = this.service.logIn(this.usuario, this.password).subscribe(
	      response =>{ 
	      	if (response.token !== 'FAIL'){

	      		console.log(response)

	      		localStorage.setItem('token',  response.token);
            	localStorage.setItem('rol',  response.rol);
            	localStorage.setItem('logo',  response.logo);
            	localStorage.setItem('empresa',  response.nombre);
            	localStorage.setItem('email',  response.email);
            	localStorage.setItem('empresaID', response.empresaID);

            	this.router.navigate(['/home']);
	      	} else {
	      		alert ('Contraseña y/o usuario incorrectos, por favor intente de nuevo');
	      	}
            
	      }         
	    );
	};

	doSuper(){
	
	let empresaID = this.empresaSelect != null ? this.empresaSelect.id : null;

		let loginServ = this.service.logInSuper(this.usuario, this.password, empresaID).subscribe(
	      response =>{ 
	      	if (response.token !== 'FAIL'){

	      		console.log(response)

	      		localStorage.setItem('token',  response.token);
            	localStorage.setItem('rol',  response.rol);
            	localStorage.setItem('logo',  response.logo);
            	localStorage.setItem('empresa',  response.nombre);
            	localStorage.setItem('email',  response.email);
            	localStorage.setItem('empresaID', response.empresaID);

            	this.router.navigate(['/home']);
	      	} else {
	      		alert ('Contraseña y/o usuario incorrectos, por favor intente de nuevo');
	      	}
            
	      }         
	    );
	};

	guardarUser(key, value){
		this[key] = value;
	};

}