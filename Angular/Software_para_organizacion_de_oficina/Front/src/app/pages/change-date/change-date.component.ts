import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';
import { Service }   			      											from 'app/service';
import { Client }               												from 'app/data-objects/cliente';
import { Trabajo }               												from 'app/data-objects/trabajo';

@Component({
  selector: 'change-date',
  templateUrl: 'change-date.component.html',
  animations: [
  ],
})

export class ChangeDateComponent implements OnInit  {
	deshabilitado : boolean = true;
	clienteID = ''
	cliente : Client

	trabajoID = ''
	trabajo : Trabajo
  constructor(    private router: Router, private route : ActivatedRoute, private service: Service
){
	this.cliente=new Client("","","","","","","","","","","",null)
	this.trabajo=null
}

	ngOnInit(): void {

		this.route
	      .queryParams
	      .subscribe(params => {
	        this.clienteID = params['cliente'];
	        this.trabajoID = params['trabajo'];

	      });

	      if (!this.clienteID)
	      	this.clienteID = ''
		


		
		this.getWorks(this.clienteID, this.trabajoID);
		console.log(this.cliente);
	};


	do(){
		this.deshabilitado = !this.deshabilitado;
	}

	buttonState(){
		return this.deshabilitado;
	}



    getWorks(idCliente, idTrabajo): void {

        this.service.getClient(idCliente).subscribe(
            response => {
                this.cliente = response;
          }        
        );

        this.service.getTrabajo(idCliente, idTrabajo).subscribe(
            response => {
                this.trabajo = response;
          }        
        );

    }
}
 