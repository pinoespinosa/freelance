import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';

import { Service }   			      from 'app/service';
import { Client }               from 'app/data-objects/cliente';


@Component({
  selector: 'register-work',
  templateUrl: 'register-work.component.html',
  animations: [
  ],
})

export class RegisterWorkComponent implements OnInit  {

	carreras = [ 'Ing. Sistemas', 'Ing. Ambiental', 'Ing. Alimentos', 'Ing. Industrial'];
	universidades = [ 'UNICEN', 'FASTA', 'CAECE', 'Siglo XXI'];
	lugarEntero = [ 'Diario', 'Television', 'Amigo'];
  	clientes : Client[];

  	id='Sin nada'

	selectUndefinedOptionValue = '';

  constructor(    private router: Router, private route : ActivatedRoute, private service: Service
){
	    this.getWorks();

	let page = '0'
    let sub = this.route
      .queryParams
      .subscribe(params => {
        // Defaults to 0 if no query param provided.
        page = params['page'];
      });

	console.log(page)

}

	do(){
		 alert("Se ha registrado los datos correctamente.");
	}


    getWorks(): void {

        let loading = this.service.getProducts().subscribe(
            response => {
                this.clientes = response;
            }        );


    }

	ngOnInit(): void {
  


	};
}
 