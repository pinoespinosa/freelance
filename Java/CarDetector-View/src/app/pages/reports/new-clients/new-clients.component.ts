import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';


@Component({
  selector: 'new-clients',
  templateUrl: 'new-clients.component.html',
  animations: [
  ],
})

export class ReportsNewClientsComponent implements OnInit  {

  constructor(    private router: Router
){}

	ngOnInit(): void {
	};


	do(){
		 alert("Se ha registrado los datos correctamente.");
	}
}
 