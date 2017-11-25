import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';
import { Service }   			      											from 'app/service';
import { Client }               												from 'app/data-objects/cliente';
import { Trabajo }               												from 'app/data-objects/trabajo';

@Component({
  selector: 'reuniones',
  templateUrl: 'reuniones.component.html',
  animations: [
  ],
})

export class ReunionesComponent implements OnInit  {


	cuerposColegiado: string[] = ["Todos"];
	asistentes: string[] = [ "Pedrito", "Juan", "Martin" ]

	paso = 1;


  constructor(    private router: Router, private route : ActivatedRoute, private service: Service
){
}

	ngOnInit(): void {



	};


	

	
}
 