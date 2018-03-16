import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';
import { Service }   			      											  from 'app/service';
import { Client }               												from 'app/data-objects/cliente';
import { Trabajo }               												from 'app/data-objects/trabajo';

import { CuerpoColegiado }               								from 'app/data-objects/cuerpoColegiado';
import { Acta }               													from 'app/data-objects/acta';
import { UsuarioActa }                                  from 'app/data-objects/usuarioActa';
import { Usuario }                                      from 'app/data-objects/usuario';
import {Observable}                                     from 'rxjs/Rx'; 
import {Subscription}                                   from 'rxjs';


@Component({
  selector: 'reuniones-edicion',
  templateUrl: 'reuniones-edicion.component.html',
  animations: [
  ],
})

export class ReunionesEdicionComponent implements OnInit  {

  showDialogAddCarre:boolean = false;

  actaSelect: Acta = null;

  usuarios : Usuario[];
  
  actaCreada: Acta;
  actaAnterior: Acta;

  actasCitadas: Acta[] = [];

  logo = ''


  paso = 1;


  constructor( 
    private router: Router, 
    private route : ActivatedRoute, 
    private service: Service)
  	{
  		this.logo = localStorage.getItem('logo');
      localStorage.setItem('empresa-creada',localStorage.getItem('empresaID'));

  	}

  ngOnInit(): void {


    let loading = this.service.getActasCitadas().subscribe(
      response => {
        this.actasCitadas = response;
      });



  };


  ngOnDestroy() {
  }

  selectActa(actaCombo): void {
    this.actaSelect = this.actasCitadas[actaCombo.selectedIndex - 1];

  }


  createActa():void{
  	this.actaCreada = new Acta("0","0","0","lugar","ciudad",[],"finGral",[],"0", "","","","","","","","","","","");
    this.paso = 2;
  }

  addUser(user):void{

    let asiiii : Usuario =  user;

    let esta:boolean = false;
    for (let aa of this.actaCreada.integrantes) {
      if (aa.userID == asiiii.userID)
        esta = true;
    }

    if (!esta){

      let provii = new UsuarioActa(asiiii.userID, asiiii.nombre, asiiii.email,"","","");
      console.log(provii)
      this.actaCreada.integrantes.push(provii);
    }
  }


  removeUser(user):void{

    let nuevo: UsuarioActa[]=[];
    let asiiii : Usuario =  user;

    let esta:boolean = false;
    for (let aa of this.actaCreada.integrantes) {
      if (aa.userID != asiiii.userID)
        nuevo.push(aa);
    }

    this.actaCreada.integrantes = nuevo;

  }

  setPaso2Info(lugar, ciudad, finMente, fecha, horaInicio, horaFinal):void{

    console.log(horaInicio)
    console.log(horaFinal)

    this.actaCreada.lugar = lugar;
    this.actaCreada.ciudad = ciudad;
    this.actaCreada.finMenteGral = finMente;

    this.actaCreada.horaInicio = horaInicio;
    this.actaCreada.horaFinal = horaFinal
    this.actaCreada.fechaReunion = fecha

//    this.getLastActa();

    if(!this.actaCreada.integrantes)
      this.actaCreada.integrantes = []

    this.paso = 3;
  }

  print():void{
    console.log(this.actaCreada.integrantes);
  }


  createActaSend( lugar, ciudad, reqq, horaInicio, horaFinal, fecha  ):void{

    this.actaSelect.lugar = lugar
    this.actaSelect.ciudad = ciudad
    this.actaSelect.finMenteGral = reqq
    this.actaSelect.horaInicio = horaInicio
    this.actaSelect.horaFinal = horaFinal
    this.actaSelect.fechaReunion = fecha
 
    if (confirm("Esta a punto de crear una reunion y enviar las invitaciones. ¿Desea continuar? (Si presiona OK, espere unos segundos)")){

      this.service.editActaReunion(this.actaSelect).subscribe(
        response =>{ 
          this.actaSelect = response;
          alert("Se ha creado la reunion Acta " + response.numeroActa + " y se han enviado las invitaciones.");    
          this.router.navigateByUrl('/home');

        }         
      );
    }
  }

  cargarFinEnMente(fin):void{

  	this.actaCreada.finMenteGral = fin;

//  	let loading = this.service.editActa(this.cuerpoColegiadoSelect.id,this.actaCreada).subscribe(
//      response =>{ 
//        this.actaCreada = response;
//        this.paso = 3;
//
//      }         
//      );
  }

  isValidateRangeHour(inicio, final): boolean {
  
    let hora1 = inicio.split(':')[0]
    let hora2 = final.split(':')[0]

    let min1 = inicio.split(':')[1]
    let min2 = final.split(':')[1]

  return hora2 > hora1 || (hora2 == hora1 && min2 > min1)


}

}
