import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';
import { Service }   			      											  from 'app/service';
import { Client }               												from 'app/data-objects/cliente';
import { Trabajo }               												from 'app/data-objects/trabajo';

import { CuerpoColegiado }               								from 'app/data-objects/cuerpoColegiado';
import { Acta }               													from 'app/data-objects/acta';
import { UsuarioActa }                                  from 'app/data-objects/usuarioActa';


@Component({
  selector: 'reuniones',
  templateUrl: 'reuniones.component.html',
  animations: [
  ],
})

export class ReunionesComponent implements OnInit  {


	cuerposColegiado: CuerpoColegiado[];
	cuerpoColegiadoSelect: CuerpoColegiado;
	
  asistentes: UsuarioActa[];

  
	actaCreada: Acta;
  actaAnterior: Acta;


	paso = 1;


  constructor( 
  private router: Router, 
  private route : ActivatedRoute, 
  private service: Service)
  {}

  ngOnInit(): void {


    let loading = this.service.getCuerpoColegiados().subscribe(
      response =>{ 
        this.cuerposColegiado = response;
      }         
    );


	};

 

 selectCuerpo(cuerpo):void{
    this.cuerpoColegiadoSelect = this.cuerposColegiado[cuerpo.selectedIndex-1];
    console.log(this.cuerpoColegiadoSelect)
 }

createActa():void{
  	this.actaCreada = new Acta("0","0","0","lugar","ciudad",null,"finGral","finEsp","temN","temasT");
		this.paso = 2;
}


setPaso2Info(lugar, ciudad, fin):void{
    this.actaCreada.lugar = lugar;
    this.actaCreada.ciudad = ciudad;
    this.actaCreada.finMenteGral = fin;
    console.log(this.actaCreada)
    this.getLastActa();
    this.paso = 3;
}

getLastActa():void{
    let loading = this.service.getLastActa(this.cuerpoColegiadoSelect.id).subscribe(
      response =>{ 
        this.actaAnterior = response;
        this.asistentes = response.integrantes;
        }         
    );

  }

createActaSend():void{

    let acta:Acta = new Acta("0","0","0","lugar","ciudad",null,"finGral","finEsp","temN","temasT");

    console.log(this.cuerpoColegiadoSelect.id)

    let loading = this.service.createActa(this.cuerpoColegiadoSelect.id,acta).subscribe(
      response =>{ 
        this.actaCreada = response;
    this.paso = 2;

      }         
    );

  }

cargarFinEnMente(fin):void{

  	this.actaCreada.finMenteGral = fin;

  	let loading = this.service.editActa(this.cuerpoColegiadoSelect.id,this.actaCreada).subscribe(
      response =>{ 
        this.actaCreada = response;
		this.paso = 3;

      }         
    );

  }
	
}
 