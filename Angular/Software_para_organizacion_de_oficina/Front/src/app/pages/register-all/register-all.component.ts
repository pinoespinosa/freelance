import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';
import { Service }   			      from 'app/service';
import { Client }               from 'app/data-objects/cliente';

import { Trabajo }              from 'app/data-objects/trabajo';
import { Requerimiento }               from 'app/data-objects/requerimiento';
import { Asesor }                 from 'app/data-objects/asesor';


@Component({
  selector: 'register-all',
  templateUrl: 'register-all.component.html',
  animations: [
  ],
})

export class RegisterAllComponent implements OnInit  {

	paso=1;

	field_nombre: string;

  	field_telef1: string;
	field_telef2: string;
	field_telef3: string;

	field_email1: string;
	field_email2: string;
	field_email3: string;


	carreras: string[];
	universidades: string[];
	lugarEntero: string[];
  asesores: Asesor[];


  requerimientos: Requerimiento[];


  clientes : Client[];
  cliSelect;
 	id='Sin nada'
	selectUndefinedOptionValue = '';

  showDialogAddUniv = false;
  showDialogAddCarre = false;
  showDialogAddLugar = false;
  showDialogAddReq = false;
  date = Date.now();

  select_cliente = ''

  	cliente:Client;




  constructor(private router: Router, private route : ActivatedRoute, private service: Service) {
    this.getWorks();
    this.getUniversidades();
    this.getCarreras();

    this.getAsesores();

    this.getDondeSeEntero();
    this.requerimientos = [];

  	let page = '0'
    let sub = this.route
      .queryParams
      .subscribe(

        result => {
          // Defaults to 0 if no query param provided.
          page = result['page'];
        }

      );

  	console.log(page)

  }

ngOnInit(): void {
	};

getOpacity(elem){

	if (elem == this.paso)
		return 1 
	else
		return 0.5
}

	update_field_nombre(valor){		this.field_nombre=valor;	}
	update_field_telef1(valor){		this.field_telef1=valor;	}
	update_field_telef2(valor){		this.field_telef2=valor;	}
	update_field_telef3(valor){		this.field_telef3=valor;	}
	update_field_email1(valor){		this.field_email1=valor;	}
	update_field_email2(valor){		this.field_email2=valor;	}
	update_field_email3(valor){		this.field_email3=valor;	}
	

	do2(){

		if (!this.field_nombre){
		    alert("Complete los campos obligatorios para continuar")
			return;
		}
		 let test = new Client("", this.field_nombre, "" , this.field_telef1, this.field_telef2, this.field_telef3, this.field_email1, this.field_email2, this.field_email3, true, null);
		 

        let loading = this.service.createCliente(test).subscribe(
            response => {
                this.cliente = response;
                this.cliSelect = response;
                
            },
	        error => {
	        	// Defaults to 0 if no query param provided.
	        	alert('Usted no tiene permisos para realizar la operacion.')
	      	} 
        );
    


	}

	  addReq(req){
    this.requerimientos.push(new Requerimiento(req, "", ""))
  }

  do(fecha, tema, univer, carrera, asesor, lugar, fecha_entrega, monto, conti){
		

		if (!this.field_nombre){
		    alert("Complete los campos obligatorios para continuar")
			return;
		}
		 let test = new Client("", this.field_nombre, "" , this.field_telef1, this.field_telef2, this.field_telef3, this.field_email1, this.field_email2, this.field_email3, true, null);
		 

         this.service.createCliente(test).subscribe(
            response => {
                this.cliente = response;
                this.cliSelect = response;
                


    if (!this.cliSelect){
      alert("Debe seleccionar un cliente para crear el trabajo")
      return;
    }

    if (!tema){
      alert("Debe detallar el tema del trabajo")
      return;
    }

    if (fecha_entrega.validity && !fecha_entrega.validity.valid){
      alert("Verifique el formato de la fecha ingresada")
      return;
    }

    let estado = ''
    if (asesor){
      estado = 'Asignado'
    }
    else{
      estado = 'Listo por revisar'
    }

    let trab = new Trabajo("",  "",tema, univer , carrera,asesor, lugar, estado, fecha_entrega, monto, monto, this.requerimientos, null);

    let loading = this.service.crearTrabajo(this.cliSelect.id,trab).subscribe(
      response => {
        let nuevoTrabajo = response;
        alert("Se ha registrado los datos correctamente.")
        if (conti)
          this.router.navigateByUrl('/register-payment?cliente='+this.id+'&trabajo='+nuevoTrabajo.id);
        else
          this.router.navigateByUrl('/home');
      },
      error => {
        // Defaults to 0 if no query param provided.
        alert('Usted no tiene permisos para realizar la operacion.')
      }         
    );






                
            },
	        error => {
	        	// Defaults to 0 if no query param provided.
	        	alert('Usted no tiene permisos para realizar la operacion.')
	      	} 
        );
  	

  }
    
  updateClii(valor){
    this.cliSelect = valor;
    this.id = this.clientes[this.cliSelect.selectedIndex-1].id;
  }
      
  getUniversidades(): void {
    let loading = this.service.getUniversidades().subscribe(
      response => {
        this.universidades = response;
        this.universidades.sort((a, b) => {
          if ( a.toUpperCase() < b.toUpperCase() ) 
            return -1 ;
          else 
            if (a.toUpperCase() > b.toUpperCase() ) 
              return 1;
            else 
              return 0;
        });
      }        
    );
  }

  getAsesores(): void {
    let loading = this.service.getAsesor().subscribe(
      response => {
        this.asesores = response;
        this.asesores.sort((a, b) => {
          if ( a.nombre.toUpperCase() < b.nombre.toUpperCase() ) 
            return -1 ;
          else 
            if (a.nombre.toUpperCase() > b.nombre.toUpperCase() ) 
              return 1;
            else 
              return 0;
        });
      }        
    );
  }

  addUniv(nuevaUniv) {
    this.service.crearUniversidad(nuevaUniv).subscribe(
      response => {
        let univ = response;
        this.universidades.push(nuevaUniv);
        this.universidades.sort((a, b) => {
        if ( a.toUpperCase() < b.toUpperCase() ) 
          return -1 ;
        else 
          if (a.toUpperCase() > b.toUpperCase() ) 
            return 1;
          else 
            return 0;
        });
      },
      error => {
        // Defaults to 0 if no query param provided.
        alert('Usted no tiene permisos para realizar la operacion.')
      } 
    );
  }

  addCarrera(nuevaCarrera) {
    this.service.crearCarrera(nuevaCarrera).subscribe(
      response => {
        let carr = response;
        this.carreras.push(nuevaCarrera);
        this.carreras.sort((a, b) => {
        if ( a.toUpperCase() < b.toUpperCase() ) 
          return -1 ;
        else 
          if (a.toUpperCase() > b.toUpperCase() ) 
            return 1;
          else 
            return 0;
        });
      },
      error => {
        // Defaults to 0 if no query param provided.
        alert('Usted no tiene permisos para realizar la operacion.')
      }        
    );
  }

  addDondeEntero(nuevaDE) {
    this.service.crearDondeEntero(nuevaDE).subscribe(
      response => {
        let lE = response;
        this.lugarEntero.push(nuevaDE);
        this.lugarEntero.sort((a, b) => {
        if ( a.toUpperCase() < b.toUpperCase() ) 
          return -1 ;
        else 
          if (a.toUpperCase() > b.toUpperCase() ) 
            return 1;
          else 
            return 0;
        });
      },
      error => {
        // Defaults to 0 if no query param provided.
        alert('Usted no tiene permisos para realizar la operacion.')
      }        
    );
  }

  getCarreras(): void {
    let loading = this.service.getCarreras().subscribe(
      response => {
        this.carreras = response;
        this.carreras.sort((a, b) => {
          if ( a.toUpperCase() < b.toUpperCase() ) 
            return -1 ;
          else 
            if (a.toUpperCase() > b.toUpperCase() ) 
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
          if ( a.toUpperCase() < b.toUpperCase() ) 
            return -1 ;
          else 
            if (a.toUpperCase() > b.toUpperCase() ) 
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
          if ( (a.nombre.toUpperCase()) < (b.nombre.toUpperCase()) ) 
            return -1 ;
          else 
            if ((a.nombre.toUpperCase()) > (b.nombre.toUpperCase()) ) 
              return 1;
            else 
              return 0;
        });
      }        
    );
  }

}
 