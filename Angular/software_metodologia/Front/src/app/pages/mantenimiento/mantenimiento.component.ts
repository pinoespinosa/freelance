import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';
import { Service }   			      											from 'app/service';
import { Client }               												from 'app/data-objects/cliente';
import { Trabajo }               												from 'app/data-objects/trabajo';
import { CuerpoColegiado }               										from 'app/data-objects/cuerpoColegiado';
import { Acta }               													from 'app/data-objects/acta';
import { Tema }                                         from 'app/data-objects/tema';
import { Tarea }                                   from 'app/data-objects/tarea';
import { Usuario }                                from 'app/data-objects/usuario';



@Component({
  selector: 'mantenimiento',
  templateUrl: 'mantenimiento.component.html',
  animations: [
  ],
})

export class MantenimientoComponent implements OnInit  {

	tipoConsulta: string[] = ["Acta Completa", "Por Fin en Mente", "Por Temas", "Por Tareas", "Por Comentarios", "Por Estrategia", "Por indicadores"];
	cuerposColegiado: CuerpoColegiado[] = [];
	cuerpoColegiadoSelect: CuerpoColegiado;
 
  estrategias: string[] = ["Todas las estrategias", "Sin Estrategia","Estrategia 1","Estrategia 2","Estrategia 3","Estrategia 4","Estrategia 4"];
  responsables : Usuario[] = [];


  logo:string = "";
  userType:string = "";

	actas: Acta[] = [];

  actaCombo : Acta;

  temasDelActa: Tema[] = [];
  temaActual: Tema = new Tema("","","",[],[],"","");
  indice = 0;

  tareasMostrar: Tarea[] = [];
  tareaActual: Tarea = new Tarea("","","",[],null);
  tareasFiltro = "Todas";

  indiceTAREA = 0;


  respon

  constructor(    private router: Router, private route : ActivatedRoute, private service: Service
){
  this.logo = localStorage.getItem('logo');
  this.userType = localStorage.getItem('rol');

}

	ngOnInit(): void {
		let loading = this.service.getCuerpoColegiados().subscribe(
      		response =>{ 
        		this.cuerposColegiado = response;
      		});
	};


	
}
 