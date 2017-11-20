import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';

import { Service }   			      from 'app/service';
import { Client }               from 'app/data-objects/cliente';
import { Trabajo }              from 'app/data-objects/trabajo';
import { Requerimiento }               from 'app/data-objects/requerimiento';
import {DatePipe} from '@angular/common';

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

  constructor(private router: Router, private route : ActivatedRoute, private service: Service) {
    this.getWorks();
    this.getUniversidades();
    this.getCarreras();
    this.getDondeSeEntero();
    this.requerimientos = [];

  	let page = '0'
    let sub = this.route
      .queryParams
      .subscribe(params => {
        // Defaults to 0 if no query param provided.
        page = params['page'];
      });

  	console.log(page)

  }

  addReq(req){
    this.requerimientos.push(new Requerimiento(req, "", ""))
  }

  do(fecha, tema, univer, carrera, asesor, lugar, fecha_entrega, monto, conti){
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

    let loading = this.service.crearTrabajo(this.clientes[this.cliSelect.selectedIndex-1].id,trab).subscribe(
      response => {
        let nuevoTrabajo = response;
        alert("Se ha registrado los datos correctamente.")
        if (conti)
          this.router.navigateByUrl('/register-payment?cliente='+this.id+'&trabajo='+nuevoTrabajo.id);
        else
          this.router.navigateByUrl('/home');
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
      }        
    );
  }

  addDondeEntero(nuevaDE) {
    this.service.crearUniversidad(nuevaDE).subscribe(
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

  ngOnInit(): void {};

}
 