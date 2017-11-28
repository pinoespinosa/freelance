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
  selector: 'sesion',
  templateUrl: 'sesion.component.html',
  animations: [
  ],
})

export class SesionComponent implements OnInit  {


	cuerpoColegiadoSelect: CuerpoColegiado;
 	cuerpoColegiadoSelectID = -1;
	actas: Acta[] = [];

  	actaCombo;

  	temasDelActa: Tema[] = [];
  	temaActual: Tema = new Tema("","","");
  	indice = 0;


  constructor(    private router: Router, private route : ActivatedRoute, private service: Service
){


    let sub = this.route
      .queryParams
      .subscribe(

        result => {
          // Defaults to 0 if no query param provided.
          this.cuerpoColegiadoSelectID = result['cc']

        }

      );

		let loading = this.service.getCuerpoColegiado(this.cuerpoColegiadoSelectID).subscribe(
      		response =>{ 
        		this.cuerpoColegiadoSelect = response;
        		this.actas = response.actas;
      		});




}

	ngOnInit(): void {

       this.service.getTemas(this.cuerpoColegiadoSelectID).subscribe(
          response =>{ 
            this.temasDelActa = response;
            if(response.length>=0)
              this.temaActual = response[0]
            this.indice=0;
            console.log(response)
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
 