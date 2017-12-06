import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';

import { Service }   			      from 'app/service';
import { Client }               from 'app/data-objects/cliente';
import { ClientFull }               from 'app/data-objects/clienteFull';

@Component({
  selector: 'to-do',
  templateUrl: 'to-do.component.html',
  animations: [
  ],
})

export class ReportsToDoComponent implements OnInit  {

	clientes : ClientFull[];
	trabajo: ClientFull;

	constructor(private router: Router, private route : ActivatedRoute, private service: Service){
		this.pendingJobs();
		this.trabajo = new ClientFull("","","","","","","","","","","","","","","");
		this.clientes=[];
	}
	
	ngOnInit(): void {};
	
	do(){};

	pendingJobs(){
        let loading = this.service.getPendingJobs().subscribe(
            response => {
                for (var cliente of response){
          			for (var trabajo of cliente.trabajos){
          				let cF = new ClientFull(
          					cliente.id,
          					cliente.nombre,
          					"",
          					trabajo.id,
          					trabajo.tema,
          					"",
          					"",
          					"",
          					trabajo.carrera,
          					trabajo.universidad,
          					trabajo.fecha_entrega,
          					"", 
          					"",
          					trabajo.asesor,"");
            			this.clientes.push(cF);
          			}
          		}
            }        
        );
	};

	addAsesor(asesor){
	 	//debugger;

    this.service.editAsesor(this.trabajo.clienteID, this.trabajo.id_trabajo, asesor).subscribe();
    this.trabajo.asesor = asesor;
	}
}
 