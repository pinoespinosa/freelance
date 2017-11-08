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

	cliente = ''
	trabajo = ''



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
	console.log("Cliente:" + this.cliente);
	console.log(this.trabajo);


	};
}
 