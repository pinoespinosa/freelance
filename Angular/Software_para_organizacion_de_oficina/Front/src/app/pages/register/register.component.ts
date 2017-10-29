import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';


@Component({
  selector: 'register',
  templateUrl: 'register.component.html',
  animations: [
  ],
})

export class RegisterComponent implements OnInit  {

  constructor(    private router: Router
){}

	ngOnInit(): void {
  


	};
}
 