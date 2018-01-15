import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute, NavigationExtras }                          from '@angular/router';
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
  selector: 'consultas-fin-mente',
  templateUrl: 'consultas-fin-mente.component.html',
  animations: [
  ],
})

export class ConsultasFinMenteComponent implements OnInit  {

  finesMente: string[] = ["Acta Completa", "Por Fin en Mente", "Por Temas", "Por Tareas", "Por Comentarios", "Por Estrategia", "Por indicadores","Acta Completa", "Por Fin en Mente", "Por Temas", "Por Tareas", "Por Comentarios", "Por Estrategia", "Por indicadores"];

  actas:any[];
  logo:string = "";

  actaSelect;
  consultaSelect;
  
  constructor(    private router: Router, private route : ActivatedRoute, private service: Service
){
  this.logo = localStorage.getItem('logo');

}

	ngOnInit(): void {

    this.service.getActasFinMente('').subscribe(
      response =>{ 
        this.actas = response;
      });


    let sub = this.route
      .queryParams
      .subscribe(

        result => {
          // Defaults to 0 if no query param provided.
          if(result['type'])
            this.consultaSelect = result['type'].split('_').join(' ')
          if(result['actaID'])
            this.actaSelect = result['actaID'].split('_').join(' ')
        }

      );

	};


  selectActa(actaID): void {

    let navigationExtras: NavigationExtras = {
      queryParams: {
        "type": this.consultaSelect.split(' ').join('_'),
        "actaID": actaID
      }
    };
  
    this.router.navigate(['/consultas'], navigationExtras);


}
 }