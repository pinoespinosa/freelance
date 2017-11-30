import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';
import { Service }   			      											from 'app/service';
import { Client }               												from 'app/data-objects/cliente';
import { Trabajo }               												from 'app/data-objects/trabajo';
import { CuerpoColegiado }               										from 'app/data-objects/cuerpoColegiado';
import { Acta }               													from 'app/data-objects/acta';
import { Tema }                                         from 'app/data-objects/tema';



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


	actas: Acta[] = [];

  actaCombo;

  temasDelActa: Tema[] = [];
  temaActual: Tema = new Tema("","","");
  indice = 0;


  respon

  constructor(    private router: Router, private route : ActivatedRoute, private service: Service
){
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
  }

  indiceTemaMenos(){
    if (this.indice > 0 ){
        this.indice = this.indice -1;
        this.temaActual = this.temasDelActa[this.indice]
  }

  }

	selectCuerpo(cuerpo):void{
    	this.cuerpoColegiadoSelect = this.cuerposColegiado[cuerpo.selectedIndex-1];
    	this.actas = this.cuerpoColegiadoSelect.actas
      this.temaActual= new Tema("","","");
      this.temasDelActa = []
      this.indice = 0;
      this.actaCombo.selectedIndex=-1
 	}

  selectActa(actaSelect):void{
      let actaaa = this.actas[actaSelect.selectedIndex-1];


      let loading = this.service.getTemas(this.cuerpoColegiadoSelect.id).subscribe(
          response =>{ 
            this.temasDelActa = response;
            if(response.length>=0)
              this.temaActual = response[0]
            this.indice=0;
            console.log(response)
          });


      this.actaCombo = actaSelect;

  }
	
}
 