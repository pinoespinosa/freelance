import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';
import { Client }               												from 'app/data-objects/cliente';
import { Trabajo }               												from 'app/data-objects/trabajo';
import { Pago }               													from 'app/data-objects/pago';

import { Service }   			      											from 'app/service';

@Component({
  selector: 'register-payment',
  templateUrl: 'register-payment.component.html',
  animations: [
  ],
})

export class RegisterPaymentComponent implements OnInit  {

	carreras = [ 'Ing. Sistemas', 'Ing. Ambiental', 'Ing. Alimentos', 'Ing. Industrial'];
	universidades = [ 'UNICEN', 'FASTA', 'CAECE', 'Siglo XXI'];
	lugarEntero = [ 'Diario', 'Television', 'Amigo'];
	metodosPago = ['Deposito', 'Tarjeta de Credito','Tarjeta de Debito', 'Efectivo', 'Cheque']
	clienteID = ''
	cliente : Client = new Client("", "", "" , "", "", "", "", "", "", null);
	clientes :Client[];
	nuevoPago: Pago = new Pago("","","1234","","")
	trabajoID = ''
	trabajo : Trabajo = new Trabajo("","","","", "","","","", "","","",null,null )

	valorValue: number = 0;
	valorSinIva: number = 0;
	valorIVA: number = 0;
	metodoPagoSelec: string = 'Deposito'


  constructor(    private router: Router, private route : ActivatedRoute, private service: Service){
	

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

	do(){        
		this.service.crearPago(this.clienteID, this.trabajoID, this.nuevoPago).subscribe(
            response => {
                let trabajosdd = response;
               	alert("Se ha registrado los datos correctamente.");

          },
	      error => {
	        // Defaults to 0 if no query param provided.
	        alert('Usted no tiene permisos para realizar la operacion.')
	      }         
        );

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

	onKey(event: KeyboardEvent) { // with type info
	    this.valorValue = Number((<HTMLInputElement>event.target).value);
	    this.valorSinIva = Number(this.valorValue/1.12);
	    this.valorIVA = this.valorValue*0.12;
	  }

	getValorSinIVA(){
		return this.valorSinIva.toFixed(2);
	}

	getValor(){
		return +this.valorValue + (+this.trabajo.monto - +this.trabajo.saldo);
	}

	getValorIVA(){
		return this.valorIVA.toFixed(2);
	}
	saveMetodoPago(e) {
		debugger
		this.metodoPagoSelec = (<HTMLInputElement>event.target).innerHTML;
	}

	addNuevoPago(key, value) {
		this.nuevoPago[key] = value;
	}
	
}
 