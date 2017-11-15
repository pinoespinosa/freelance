import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';
import { Service }   			      											from 'app/service';
import { Client }               												from 'app/data-objects/cliente';
import { Trabajo }               												from 'app/data-objects/trabajo';


@Component({
  selector: 'more-details',
  templateUrl: 'more-details.component.html',
  animations: [
  ],
})

export class MoreDetailsComponent implements OnInit  {
  	deshabilitado : boolean = true;

	clienteID = ''
	cliente : Client = new Client("", "", "" , "", "", "", "", "", "", null);

	trabajoID = ''
  trabajo : Trabajo = new Trabajo("","","","", "","","","", "","","",null,null )


  constructor(    private router: Router, private route : ActivatedRoute, private service: Service
){
	

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
 