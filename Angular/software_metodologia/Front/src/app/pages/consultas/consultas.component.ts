import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';
import { Service }   			      											from 'app/service';
import { Client }               												from 'app/data-objects/cliente';
import { Trabajo }               												from 'app/data-objects/trabajo';

@Component({
  selector: 'consultas',
  templateUrl: 'consultas.component.html',
  animations: [
  ],
})

export class ConsultasComponent implements OnInit  {

	tipoConsulta: string[] = ["Acta Completa", "Por Fin en Mente", "Por Temas", "Por Tareas", "Por Comentarios", "Por Estrategia", "Por indicadores"];
	cuerposColegiado: string[] = ["Todos"];
	actas: string[] = ["Todos"];



  constructor(    private router: Router, private route : ActivatedRoute, private service: Service
){
}

	ngOnInit(): void {

	};


	

	
}
 