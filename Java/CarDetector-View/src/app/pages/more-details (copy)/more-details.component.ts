import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';


@Component({
  selector: 'more-details',
  templateUrl: 'more-details.component.html',
  animations: [
  ],
})

export class MoreDetailsComponent implements OnInit  {

  constructor(    private router: Router
){}

	ngOnInit(): void {
	};


	do(){
		 alert("Se ha registrado los datos correctamente.");
	}
}
 