import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';
import { Service }                                      from 'app/service';
import { Client }                                       from 'app/data-objects/cliente';
import { Trabajo }                                      from 'app/data-objects/trabajo';

import { CuerpoColegiado }                              from 'app/data-objects/cuerpoColegiado';
import { CuerpoColegiadoSelect }                  from 'app/data-objects/cuerpoColegiadoSelect';
import { Acta }                                         from 'app/data-objects/acta';
import { UsuarioActa }                                  from 'app/data-objects/usuarioActa';
import { Usuario }                                      from 'app/data-objects/usuario';


@Component({
  selector: 'create-user',
  templateUrl: 'create-user.component.html',
  animations: [
  ],
})

export class CreateUserComponent implements OnInit  {

  visiblePop : boolean = false;
  cuerposColegiado: CuerpoColegiadoSelect[];


  constructor( 
    private router: Router, 
    private route : ActivatedRoute, 
    private service: Service)
  {

    this.cuerposColegiado = [];

  }

  ngOnInit(): void {

    this.service.getCuerpoColegiados().subscribe(
      response =>{ 

        for (let cc of response) {
          this.cuerposColegiado.push(new CuerpoColegiadoSelect(cc.id, cc.nombre, cc.actas));
        }
      });

  };

  createUser(nombre, email):void{

    let listaCC='';

    for (let cc of this.cuerposColegiado) {
      if (cc.check)
        listaCC = listaCC + '&ccList=' + cc.id;
    }

    this.service.createUser(nombre, email, listaCC).subscribe(
      response =>{ 
        alert("Se ha creado el usuario exitosamente.")
        localStorage.setItem('REFRESH_USERS', 'TRUE');

      });
  }
  
  update(a, b) {
    a.check = b.checked;
  }

}
