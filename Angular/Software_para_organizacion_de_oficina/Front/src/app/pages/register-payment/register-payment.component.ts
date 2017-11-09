import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';


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

	cliente = '';
	trabajo = '';

	valorValue: number = 0;
	valorSinIva: number = 0;
	valorIVA: number = 0;



  constructor(    private router: Router, private route : ActivatedRoute
){
	



}

	do(){
		 alert("Se ha registrado los datos correctamente.");
	}


	ngOnInit(): void {
  
    this.route
      .queryParams
      .subscribe(params => {
        this.cliente = params['cliente'];
        this.trabajo = params['trabajo'];
      });

      if (!this.cliente)
      	this.cliente = ''
	console.log(this.cliente);
	console.log(this.trabajo);

	};

	onKey(event: KeyboardEvent) { // with type info
	    this.valorValue = Number((<HTMLInputElement>event.target).value);
	    this.valorSinIva = Number(this.valorValue/1.12);
	    this.valorIVA = this.valorValue*0.12;
	    console.log(this.valorIVA); 
	  }

	getValorSinIVA(){
		return this.valorSinIva.toFixed(2);
	}
	getValorIVA(){
		return this.valorIVA.toFixed(2);
	}
}
 