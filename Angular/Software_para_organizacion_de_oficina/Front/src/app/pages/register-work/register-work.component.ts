import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';


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

  constructor(    private router: Router
){
	

}

	do(){
		 alert("Se ha registrado los datos correctamente.");
	}


	ngOnInit(): void {
  


	};
}
 