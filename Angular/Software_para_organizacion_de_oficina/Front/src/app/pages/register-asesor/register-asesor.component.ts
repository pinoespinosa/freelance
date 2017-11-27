import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';
import { Service }   			      from 'app/service';
import { Asesor }               from 'app/data-objects/asesor';


@Component({
  selector: 'register-asesor',
  templateUrl: 'register-asesor.component.html',
  animations: [
  ],
})

export class RegisterAsesorComponent implements OnInit  {

	field_nombre: string;
  	
  	date = new Date();

  

  constructor(    
  	private router: Router,
  	private service: Service
){
	
}

	ngOnInit(): void {
	};

	update_field_nombre(valor){		this.field_nombre=valor;	}


	do(){

		if (!this.field_nombre){
		    alert("Complete los campos obligatorios para continuar")
			return;
		}
		 let test = new Asesor("", this.field_nombre);
		 

        let loading = this.service.createAsesor(test).subscribe(
            response => {
                alert("Se ha creado el asesor " + this.field_nombre + " exitosamente")
                this.router.navigateByUrl('/home');

            },
	        error => {
	        	// Defaults to 0 if no query param provided.
	        	alert('Usted no tiene permisos para realizar la operacion.')
	      	} 
        );
    


	}




}
 