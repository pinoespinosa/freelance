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

import { DatePipe } from '@angular/common';



@Component({
  selector: 'consultas-tarea',
  templateUrl: 'consultas-tarea.component.html',
  animations: [
  ],
})

export class ConsultasTareaComponent implements OnInit  {

  private logo

  cuerposColegiadoList: CuerpoColegiado[] = [];
  cuerpoColegiadoSelect: CuerpoColegiado;

  responsableList : Usuario[] = [];
  responsableSelect : Usuario = null;

  tareaList: Tarea[] = null

  tareaListCalendar: any = null

  fechaCalendario = new Date();
  fechaCalendarioString 


  vistaLista: boolean = true;

  constructor(    private router: Router, private route : ActivatedRoute, private service: Service, private datePipe: DatePipe){
    this.logo = localStorage.getItem('logo');
  }

	ngOnInit(): void {
    	this.service.getCuerpoColegiados().subscribe(
          response =>{ 
            this.cuerposColegiadoList = response;
          });

    	this.service.getResponsables().subscribe(
          response =>{ 
            this.responsableList = response;
          });

        this.fechaCalendarioString = this.datePipe.transform( this.fechaCalendario, 'MM-yyyy');
 

	};


    cambiarVista(){
      this.vistaLista = !this.vistaLista;
   }

	mesMas(){

		while ( this.fechaCalendarioString.includes(this.datePipe.transform( this.fechaCalendario, 'MM-yyyy'))){
			  this.fechaCalendario.setDate( this.fechaCalendario.getDate() + 1 );
		}

		this.fechaCalendarioString = this.datePipe.transform( this.fechaCalendario, 'MM-yyyy');
    	this.find();

	}

	mesMenos(){

		while ( this.fechaCalendarioString.includes(this.datePipe.transform( this.fechaCalendario, 'MM-yyyy'))){
			  this.fechaCalendario.setDate( this.fechaCalendario.getDate() - 1 );
		}

		this.fechaCalendarioString = this.datePipe.transform( this.fechaCalendario, 'MM-yyyy');
    	this.find();


	}

    setCuerpo(cuerpo):void{


    	if (cuerpo.value.includes("Todos los Cuerpos Colegiados")){
      		this.cuerpoColegiadoSelect = null
    	} 
    	else {
      		this.cuerpoColegiadoSelect = this.cuerposColegiadoList[cuerpo.selectedIndex-1];
    	} 

    	this.find();
    	console.log(this.cuerpoColegiadoSelect)

  }

    setResponsable(respon){

    	if (respon.value.includes("Todos los Responsables")){
      		this.responsableSelect = null
    	} 
    	else {
    		this.responsableSelect = this.responsableList[respon.selectedIndex-1];
    	} 

    	console.log(respon)
    	this.find();

   }

  alert(tarea){

    let texto = ''
    for (let e of tarea.eventos){
      texto += '    * ' + e + '\n'
    }


    alert(
      tarea.fechaCreacion + '\n\n'+
      'Tarea del Acta ' + tarea.temaId + '\n\n'+
      'Responsable: ' + tarea.responsable.nombre + '\n\n' +
      'Comentarios: \n' +
      texto + '\n\n' 


    )
  }




  find ():void {

    let cuerpoValue = 'Todas'
    if (this.cuerpoColegiadoSelect)
      cuerpoValue = this.cuerpoColegiadoSelect.id;

    let responsValue = 'Todos'
    if (this.responsableSelect)
      responsValue = this.responsableSelect.userID;


      let loading = this.service.getActasFiltradas(cuerpoValue, responsValue).subscribe(
          response =>{ 
            this.tareaList = response;


          });

      this.service.getActasFiltradasCalendario(cuerpoValue, responsValue, this.fechaCalendarioString).subscribe(
          response =>{ 

          this.tareaListCalendar = response;

          });


  }







  }
 


