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

	field_nombre: string;
  	
	field_telef1: string;
	field_telef2: string;
	field_telef3: string;

	field_email1: string;
	field_email2: string;
	field_email3: string;

  	date = new Date();

  	cliente:Client;
  

  constructor(    
  	private router: Router,
  	private service: Service
){
	
}

	ngOnInit(): void {
	};


	do(){
		 let test = new Client("", this.field_nombre, "" , this.field_telef1, this.field_telef2, this.field_telef3, this.field_email1, this.field_email2, this.field_email3, null);
		 

        let loading = this.service.createCliente(test).subscribe(
            response => {
                this.cliente = response;
                alert("Se ha creado el cliente " + this.field_nombre + " exitosamente")
            }
        );
    


	}


	update_field_nombre(valor){		this.field_nombre=valor;	}
	update_field_telef1(valor){		this.field_telef1=valor;	}
	update_field_telef2(valor){		this.field_telef2=valor;	}
	update_field_telef3(valor){		this.field_telef3=valor;	}
	update_field_email1(valor){		this.field_email1=valor;	}
	update_field_email2(valor){		this.field_email2=valor;	}
	update_field_email3(valor){		this.field_email3=valor;	}

}
 