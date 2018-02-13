import { Component, OnInit, OnDestroy, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';
import { Service }                                      from 'app/service';
import { Client }                                       from 'app/data-objects/cliente';
import { Trabajo }                                      from 'app/data-objects/trabajo';

import { CuerpoColegiado }                              from 'app/data-objects/cuerpoColegiado';
import { CuerpoColegiadoSelect }                        from 'app/data-objects/cuerpoColegiadoSelect';
import { Acta }                                         from 'app/data-objects/acta';
import { UsuarioActa }                                  from 'app/data-objects/usuarioActa';
import { Usuario }                                      from 'app/data-objects/usuario';
import {Observable}                                     from 'rxjs/Rx'; 
import {Subscription}                                   from 'rxjs';


@Component({
  selector: 'create-user',
  templateUrl: 'create-user.component.html',
  animations: [
  ],
})

export class CreateUserComponent implements OnInit, OnDestroy  {

  visiblePop : boolean = false;
  cuerposColegiado: CuerpoColegiadoSelect[];
  empresaCreada : string = '';

  perfiles = ['ADMINISTRADOR', 'GENERAL', 'SOLO_CONSULTA']

  constructor( 
    private router: Router, 
    private route : ActivatedRoute, 
    private service: Service)
  {
    this.cuerposColegiado = [];
    this.refreshAll();

  }

  temporizador = Observable.interval(100).map(
  ()=> this.refresh()
  );

  ngOnDestroy(): void {
  }

  refreshAll(){

      let val = localStorage.getItem('empresa-creada')
      this.service.getCuerpoColegiadosSimple(val).subscribe(
      response =>{ 

        this.cuerposColegiado = []
        for (let cc of response) {
          this.cuerposColegiado.push(new CuerpoColegiadoSelect(cc.id, cc.nombre, cc.actas));
        }
        this.empresaCreada = localStorage.getItem('empresa-creada');


      });
}

  refresh():boolean{

    if (this.empresaCreada != localStorage.getItem('empresa-creada'))
{

    this.refreshAll();
}
return true;
  }

  ngOnInit(): void {

    this.refreshAll();

  };

  isValid(nombre, email, perfil) : boolean{

    let listaCC='';

    for (let cc of this.cuerposColegiado) {
      if (cc.check)
        listaCC = listaCC + '&ccList=' + cc.id;
    }

    return listaCC != '' && nombre!= '' && email!= '';


  }


  createUser(nombre, email, perfil ):void{

    let listaCC='';

    for (let cc of this.cuerposColegiado) {
      if (cc.check)
        listaCC = listaCC + '&ccList=' + cc.id;
    }

    if (!listaCC)
        alert("Debe seleccionar un cuerpo colegiado.")
    else{

      this.empresaCreada = localStorage.getItem('empresa-creada');

      if (this.empresaCreada!=''){


    this.service.createUserAdmin(nombre, email, listaCC, this.empresaCreada, perfil).subscribe(
      response =>{ 
        alert("Se ha creado el usuario exitosamente.")
        localStorage.setItem('REFRESH_USERS', 'TRUE');
      },
      error =>{ 
        alert("Ya existe un usuario con ese email registrado. No se ha creado el usuario")
        localStorage.setItem('REFRESH_USERS', 'TRUE');
      },

      );

      }
else
{

    this.service.createUser(nombre, email, listaCC).subscribe(
      response =>{ 
        alert("Se ha creado el usuario exitosamente.")
        localStorage.setItem('REFRESH_USERS', 'TRUE');
      },
      error =>{ 
        alert("Ya existe un usuario con ese email registrado. No se ha creado el usuario")
        localStorage.setItem('REFRESH_USERS', 'TRUE');
      },

      );
  }
      }
  }
  
  update(a, b) {
    a.check = b.checked;
  }

}
