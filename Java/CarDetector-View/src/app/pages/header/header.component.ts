import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';


@Component({
  selector: 'app-header',
  templateUrl: 'header.component.html',
  animations: [
    trigger(
      'modalAnimation', [

        transition(':enter',
          [
            style({opacity: 0, display: 'block'}),
            animate( '500ms', style({opacity: 1}) )
          ]
        ),
        transition(':leave',
          [
            style({  }),
            animate( '500ms', style({opacity: 0}) )
          ]
        )
      ]
    )
  ],
})

export class HeaderComponent implements OnInit  {

	ngOnInit(): void {
  


	};
}
 