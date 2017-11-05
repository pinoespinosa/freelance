import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';


@Component({
  selector: 'to-do',
  templateUrl: 'to-do.component.html',
  animations: [
  ],
})

export class ReportsToDoComponent implements OnInit  {

  constructor(    private router: Router
){}

	ngOnInit(): void {
	};


	do(){
		 alert("Se ha registrado los datos correctamente.");
	}
}
 