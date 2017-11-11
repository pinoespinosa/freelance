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

	carreras: string[];
	universidades: string[];
	lugarEntero: string[];
  clientes : Client[];
 	id='Sin nada'
	selectUndefinedOptionValue = '';

  showDialogAddUniv = false;
  showDialogAddCarre = false;
  showDialogAddLugar = false;

  constructor(    private router: Router, private route : ActivatedRoute, private service: Service
){
	    this.getWorks();
      this.getUniversidades();
      this.getCarreras();
      this.getDondeSeEntero();

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
 
    getUniversidades(): void {

        let loading = this.service.getUniversidades().subscribe(
            response => {
                this.universidades = response;
                this.universidades.sort((a, b) => {
                  if ( a < b ) 
                    return -1 ;
                  else 
                    if (a > b ) 
                      return 1;
                    else 
                      return 0;
                });
            }        
        );
    }

    getCarreras(): void {

        let loading = this.service.getCarreras().subscribe(
            response => {
                this.carreras = response;
                this.carreras.sort((a, b) => {
                  if ( a < b ) 
                    return -1 ;
                  else 
                    if (a > b ) 
                      return 1;
                    else 
                      return 0;
                });
            }        
        );
    }

    getDondeSeEntero(): void {

        let loading = this.service.getDondeSeEntero().subscribe(
            response => {
                this.lugarEntero = response;
                this.lugarEntero.sort((a, b) => {
                  if ( a < b ) 
                    return -1 ;
                  else 
                    if (a > b ) 
                      return 1;
                    else 
                      return 0;
                });
            }        
        );
    }



    getWorks(): void {

        let loading = this.service.getProducts().subscribe(
            response => {
                this.clientes = response;
                this.clientes.sort((a, b) => {
                  if ( (a.nombre) < (b.nombre) ) 
                    return -1 ;
                  else 
                    if ((a.nombre) > (b.nombre) ) 
                      return 1;
                    else 
                      return 0;
                });
            }        
        );



    }

	ngOnInit(): void {
  


	};
}
 