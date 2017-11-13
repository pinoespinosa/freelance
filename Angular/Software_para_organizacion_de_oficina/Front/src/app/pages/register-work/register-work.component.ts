import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';

import { Service }   			      from 'app/service';
import { Client }               from 'app/data-objects/cliente';
import { Trabajo }              from 'app/data-objects/trabajo';


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

  requerimientos: string[];


  clientes : Client[];
 	id='Sin nada'
	selectUndefinedOptionValue = '';

  showDialogAddUniv = false;
  showDialogAddCarre = false;
  showDialogAddLugar = false;
  showDialogAddReq = false;


  select_univ = ''
  select_carr = ''
  select_lugar = ''
  select_cliente = ''

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

  updateUniv(uni){    this.select_univ = uni;   }
  updateCarr(car){    this.select_carr = car;   }
  updateLugar(lug){   this.select_lugar = lug;   }


	do(){

    console.log(this.select_univ)
    console.log(this.select_carr)
    console.log(this.select_lugar)

    alert("Se ha registrado los datos correctamente.")

    let trab = new Trabajo("","tema","titulo",this.select_univ,"monto","entrega","dondeSeEntero","estado","fecha", "fecha_entrega", "observaciones", "observaciones_next", null, null);


    let loading = this.service.crearTrabajo("1",trab).subscribe(
      response => {
      }        
    );


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
 