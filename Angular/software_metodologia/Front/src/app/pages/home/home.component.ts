import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';

import { Client }                   from 'app/data-objects/cliente';
import { ClientFull }               from 'app/data-objects/clienteFull';
import { Requerimiento }               from 'app/data-objects/requerimiento';


import { Service }   			          from 'app/service';


@Component({
  selector: 'home',
  templateUrl: 'home.component.html',
  animations: [
  ],
})

export class HomeComponent   {

  title = 'app';
  clientes : Client[];
  requerimientos: string[];

  showDialogAddReq = false;
  showDialogChangeDate = false;

  tipo_orden = 1;

  cliente: Client;
  cliente2: ClientFull;



  items : ClientFull[];
  items_orig : ClientFull[];

  estados: string[] = ['Urgente', 'Asignado','Trabajando','Listo por revisar','Avance Asesor Listo', 'Listo por pagar/enviar', 'Pendiente para t/ luego'];

  esstado:string;


  showDialog = false;


  constructor(private service: Service) {
    this.getWorks();
    this.items=[];
    this.items_orig = [];
    this.cliente = new Client("","","", "","","", "","","",null);
    this.cliente2 = new ClientFull("","","","","","","","","","","","","","");

  }

  callType(est){
    this.esstado = est;

  }


  addReq(req){

    let re = new Requerimiento(req,"","");

    let loading = this.service.agregarRequerimiento(this.cliente2.clienteID, this.cliente2.id_trabajo, re).subscribe(
      response =>{ 
        let aa = response;
      }         
    );
  }



  sortNombre(){
    this.items.sort((a, b) => {
      if ( (a.nombre +a.apellido) < (b.nombre + b.apellido) ) 
        return -1 * this.tipo_orden;
      else 
        if ((a.nombre +a.apellido) > (b.nombre + b.apellido) ) 
          return 1 * this.tipo_orden;
        else 
          return 0;
    });
    this.tipo_orden = this.tipo_orden * -1
  }

  sortFecha(){
    this.items.sort((a, b) => {
      if ( (a.entrega) < (b.entrega) )
        return -1 * this.tipo_orden;
      else 
        if ((a.entrega) > (b.entrega) ) 
          return 1 * this.tipo_orden;
        else 
          return 0;
    });
    this.tipo_orden = this.tipo_orden * -1 
  }

  sortEstado(){

    this.items.sort((a, b) => {

      if ( (a.ordenEstado) < (b.ordenEstado) )
        return -1 * this.tipo_orden;
      else 
        if ((a.ordenEstado) > (b.ordenEstado) ) 
          return 1 * this.tipo_orden;
        else 
          return 0;
    });
    this.tipo_orden = this.tipo_orden * -1 
    console.log(this.items)
  }

  sortUniversidad(){
    this.items.sort((a, b) => {
      if ( (a.universidad) < (b.universidad) )
        return -1 * this.tipo_orden;
      else 
        if ((a.universidad) > (b.universidad) ) 
          return 1 * this.tipo_orden;
        else 
          return 0;
    });
    this.tipo_orden = this.tipo_orden * -1 
  }


  changeState(cliente, trabajo, estado){
    

        let loading = this.service.getChangeStateTrabajo(cliente, trabajo, estado).subscribe(
            response =>{ 
              let aa = response;

              this.getWorks();
            }         
        );
        alert("Se ha actualizado el estado.")


  }

  updateFechaEntrega(fechaNueva){
    
    if (fechaNueva.validity && !fechaNueva.validity.valid){
      alert("La fecha ingresada no cumple con el formato o es invalida")
      return;
    }

    if (!fechaNueva.value){
      alert("La fecha ingresada no cumple con el formato o es invalida")
      return;
    }
        let loading = this.service.updateFechaEntrega(this.cliente2.clienteID, this.cliente2.id_trabajo, fechaNueva.value).subscribe(
            response =>{ 
              let aa = response;

              this.getWorks();
            }         
        );
        alert("Se ha actualizado el estado.")


  }


  myFunction(valor){
    
    this.items = [];
    console.log(valor)
    for (var aa of this.items_orig){

      if ( aa.nombre.toLowerCase().includes(valor.toLowerCase()) ||  aa.tema.toLowerCase().includes(valor.toLowerCase()) ){
        this.items.push(aa);
      }
    } 
  }

  getWorks(): void {

    console.log("update front")
    this.items=[];
    this.items_orig = [];
    let loading = this.service.getProducts().subscribe(
      response => {
        this.clientes = response;
        for (var aa of response){
          for (var bb of aa.trabajos){
            let result = ""
            switch(bb.estado) { 
     					case 'Urgente': { 
  					    result='1'; 
  					    break; 
  						} 
     					case 'Asignado': { 
  					    result='2'; 
  					    break; 
  						} 
     					case 'Trabajando': { 
  					    result='3'; 
  					    break; 
  						}
     					case 'Listo por revisar': { 
  					    result='4'; 
  					    break; 
  						} 						
     					case 'Avance Asesor Listo': { 
  					    result='5'; 
  					    break; 
  						} 
     					case 'Listo por pagar/enviar': { 
  					    result='6'; 
  					    break; 
  						} 	
     					case 'Pendiente para t/ luego': { 
  					    result='7'; 
  					    break; 
  						} 
  						default: { 
  					    result='8'; 
       					break; 
  						} 
  					} 
            let fu = new ClientFull(aa.id,aa.nombre,"",bb.id,bb.tema,"",bb.monto,bb.saldo,"",bb.universidad,bb.fecha_entrega,bb.estado, result + '_' + bb.fecha_entrega,"");
            this.items.push(fu);
            this.items_orig.push(fu);
          }
        }
	    this.tipo_orden = 1;
      this.sortEstado();
      }        
    );
  }
}
 