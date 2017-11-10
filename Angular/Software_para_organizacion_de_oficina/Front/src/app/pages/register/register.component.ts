import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';
import { Service }   			      from 'app/service';
import { Client }               from 'app/data-objects/cliente';


@Component({
  selector: 'register',
  templateUrl: 'register.component.html',
  animations: [
  ],
})

export class RegisterComponent implements OnInit  {

 	name: string;
	field_nombre: string;
  	
	field_telef1: string;
	field_telef2: string;
	field_telef3: string;

  	date = new Date();

  

  constructor(    
  	private router: Router,
  	private service: Service
){
	
}

	ngOnInit(): void {
	};


	do(){
		 let test = new Client("","","","","","","","","","","",null);
		 

        let loading = this.service.createCliente(test).subscribe(
            response => {
                let stateInfo = response;
                console.log(stateInfo)
            }
        );
    


	}


	update_field_nombre(valor){		this.field_nombre=valor;	}
	update_field_telef1(valor){		this.field_telef1=valor;	}
	update_field_telef2(valor){		this.field_telef2=valor;	}
	update_field_telef3(valor){		this.field_telef3=valor;	}


}
 