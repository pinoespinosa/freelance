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



@Component({
  selector: 'consultas',
  templateUrl: 'consultas.component.html',
  animations: [
  ],
})

export class ConsultasComponent implements OnInit  {

	tipoConsulta: string[] = ["Acta Completa", "Por Fin en Mente", "Por Temas", "Por Tareas", "Por Comentarios", "Por Estrategia", "Por indicadores"];
	cuerposColegiado: CuerpoColegiado[] = [];
	cuerpoColegiadoSelect: CuerpoColegiado;
 
  estrategias: string[] = ["Estrategia 1","Estrategia 2","Estrategia 3","Estrategia 4","Estrategia 4"];
  responsables: string[] = ["Responsable 1","Responsable 2","Responsable 3","Responsable 4","Responsable 5"];
  temas: string[] = ["Tema 1","Tema 2","Tema 3","Tema 4","Tema 5"];

  logo:string = "";

	actas: Acta[] = [];

  actaCombo;

  temasDelActa: Tema[] = [];
  temaActual: Tema = new Tema("","","",[],[]);
  indice = 0;

  tareasMostrar: Tarea[] = [];
  tareaActual: Tarea = new Tarea("","","",[],null);
  tareasFiltro = "Todas";

  indiceTAREA = 0;


  respon

  constructor(    private router: Router, private route : ActivatedRoute, private service: Service
){
  this.logo = localStorage.getItem('logo');

}

	ngOnInit(): void {
		let loading = this.service.getCuerpoColegiados().subscribe(
      		response =>{ 
        		this.cuerposColegiado = response;
      		});
	};

  indiceTemaMas(){
    console.log("ddd")
      if (this.indice < this.temasDelActa.length -1){
        this.indice = this.indice +1;
        this.temaActual = this.temasDelActa[this.indice]
      }
          this.updateTareas();

  }

  indiceTemaMenos(){
    if (this.indice > 0 ){
        this.indice = this.indice -1;
        this.temaActual = this.temasDelActa[this.indice]
  }

  }

  indiceTareaMas(){
    if (this.indiceTAREA < this.tareasMostrar.length -1){
      this.indiceTAREA = this.indiceTAREA +1;
      this.tareaActual = this.tareasMostrar[this.indiceTAREA];
    }
        this.updateTareas();

  }

  indiceTareaMenos(){
    if (this.indiceTAREA > 0 ){
        this.indiceTAREA = this.indiceTAREA -1;
        this.tareaActual = this.tareasMostrar[this.indiceTAREA];
    }
  }


	selectCuerpo(cuerpo):void{
    	this.cuerpoColegiadoSelect = this.cuerposColegiado[cuerpo.selectedIndex-1];
    	this.actas = this.cuerpoColegiadoSelect.actas
      this.temaActual= new Tema("","","",[],[]);
      this.temasDelActa = []
      this.indice = 0;
 	}

  updateTareas(){
    this.tareasMostrar = [];
    for (let aa of this.temaActual.tareas) {

      if (this.tareasFiltro=="Todas")
        this.tareasMostrar.push(aa);
      else{
        if (aa.estado == this.tareasFiltro)
          this.tareasMostrar.push(aa);
        }
    }

        this.tareasMostrar.sort((a, b) => {
        if ( (+a.id) < (+b.id) ) 
          return 1;
        else 
          if ((+a.id) > (+b.id) ) 
            return -1 ;
          else 
            return 0;
    });


    if (this.tareasMostrar.length > 0){
      this.tareaActual = this.tareasMostrar[0];
      this.indiceTAREA = 0;


    } 
    else{
      this.tareaActual = new Tarea("","","",[],null);
      this.indiceTAREA = -1;
    }

  }

  updateClii(cuerpo):void{

  }

  selectActa(actaSelect):void{
      let actaaa = this.actas[actaSelect.selectedIndex-1];


      let loading = this.service.getTemasConsulta(this.cuerpoColegiadoSelect.id, actaaa.id).subscribe(
          response =>{ 
            this.temasDelActa = response;
            if(response.length>=0)
              this.temaActual = response[0]
            this.indice=0;
            console.log(response)
          });

    this.updateTareas();


      this.actaCombo = actaSelect;

  }

    toString(array){

  let ff='\n\n';

  for (let aa of array) {

  ff = ff + aa.texto + '\n'

  }

    return ff;
  }

	
}
 