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
  selector: 'reuniones',
  templateUrl: 'reuniones.component.html',
  animations: [
  ],
})

export class ReunionesComponent implements OnInit  {

  showDialogAddCarre:boolean = false;
  cuerposColegiado: CuerpoColegiado[];
  cuerpoColegiadoSelect: CuerpoColegiado;

  usuarios : Usuario[];
  
  actaCreada: Acta;
  actaAnterior: Acta;

  subscription: Subscription;


  paso = 1;


  constructor( 
    private router: Router, 
    private route : ActivatedRoute, 
    private service: Service)
  {}

  ngOnInit(): void {


    this.service.getCuerpoColegiados().subscribe(
      response =>{ 
        this.cuerposColegiado = response;
      });

    this.subscription = Observable.interval(1000 * 1).subscribe(x => { 
      if (localStorage.getItem('REFRESH_USERS')=='TRUE') {
        this.service.getUsuarios(this.cuerpoColegiadoSelect.id).subscribe(
          response =>{ 
            this.usuarios = response;
            localStorage.setItem('REFRESH_USERS', 'FALSE');

          });   
      }
    });



  };


  ngOnDestroy() {
    this.subscription.unsubscribe();
  }



  selectCuerpo(cuerpo):void{
    this.cuerpoColegiadoSelect = this.cuerposColegiado[cuerpo.selectedIndex-1];
    console.log(this.cuerpoColegiadoSelect)
    this.service.getUsuarios(this.cuerpoColegiadoSelect.id).subscribe(
      response =>{ 
        this.usuarios = response;
      });
  }

  createActa():void{
  	this.actaCreada = new Acta("0","0","0","lugar","ciudad",[],"finGral",[],"0", "","","","","","","","","");
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

    this.actaCreada.lugar = lugar;
    this.actaCreada.ciudad = ciudad;
    this.actaCreada.finMenteGral = finMente;

    this.actaCreada.horaInicio = horaInicio;
    this.actaCreada.horaFinal = horaFinal
    this.actaCreada.fechaReunion = fecha

    this.getLastActa();

    if(!this.actaCreada.integrantes)
      this.actaCreada.integrantes = []

    this.paso = 3;
  }

  print():void{
    console.log(this.actaCreada.integrantes);
  }

  getLastActa():void{
    let loading = this.service.getLastActa(this.cuerpoColegiadoSelect.id).subscribe(
      response =>{ 
        this.actaAnterior = response;
        this.actaCreada.integrantes = response.integrantes;
      }         
      );
  }

  createActaSend():void{

    console.log(this.actaCreada);

    if (confirm("Esta a punto de crear una reunion y enviar las invitaciones. ¿Desea continuar?")){


      let loading = this.service.createActa(this.cuerpoColegiadoSelect.id, this.actaCreada).subscribe(
        response =>{ 
          this.actaCreada = response;
          alert("Se ha creado la reunion Acta " + response.numeroActa + " y se han enviado las invitaciones.")
          this.router.navigateByUrl('/home');
        }         
        );
    }
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
