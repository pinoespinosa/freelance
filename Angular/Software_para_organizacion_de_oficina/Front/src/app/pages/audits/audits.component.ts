import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';
import { Service }   			      											from 'app/service';
import { Auditoria }               												from 'app/data-objects/auditoria';

@Component({
  selector: 'audits',
  templateUrl: 'audits.component.html',
  animations: [
  ],
})

export class AuditsComponent implements OnInit  {
	auditoriaArr: Auditoria[];
  	items_orig : Auditoria[];
	
	constructor(private router: Router, private route : ActivatedRoute, private service: Service) {
		this.auditoriaArr = null;
    	this.items_orig = [];
	}

	ngOnInit(): void {
        this.service.getAudit().subscribe(
            response => {
                this.auditoriaArr = response;
                this.items_orig = response;
          }        
        );
	};

	do(){}

	myFunction(valor){
    	this.auditoriaArr = [];
	    for (var aa of this.items_orig){
	      	if ( aa.usuario.toLowerCase().includes(valor.toLowerCase()) ||  aa.rol.toLowerCase().includes(valor.toLowerCase()) ){
	        this.auditoriaArr.push(aa);
	      	}
	    } 
	};

}
 