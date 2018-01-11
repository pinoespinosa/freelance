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
  selector: 'create-empresa',
  templateUrl: 'create-empresa.component.html',
  animations: [
  ],
})

export class CreateEmpresaComponent implements OnInit  {

  titulo : string = 'Administrar Empresas'

  visiblePop : boolean = false;

  modoCreacion : boolean = false;

  nombreAux : string = ''
  logoAux : string = ''

  cuerposColegiado: CuerpoColegiadoSelect[];
  empresas: any[];


  constructor( 
    private router: Router, 
    private route : ActivatedRoute, 
    private service: Service)
  {

    this.cuerposColegiado = [];

  }

  ngOnInit(): void {
    this.refreshEmpresas();
  };

  updateEmpresa(id, nombre, logo):void{

  console.log(nombre)

  let emp = {
  'id': id,
  'nombreEmpresa' : nombre,
  'logoEmpresa' : logo
  }

    this.service.updateEmpresa(emp).subscribe(
      response =>{ 
        alert("Se ha actualizado la empresa exitosamente.")
      //  this.refreshEmpresas();
      },
      error =>{ 
      },

      );
  
  }


  refreshEmpresas(){
    this.service.getEmpresas().subscribe(
      response =>{ 
        this.empresas = response;
      });


  }

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
      },
      error =>{ 
        alert("Ya existe un usuario con ese email registrado. No se ha creado el usuario")
        localStorage.setItem('REFRESH_USERS', 'TRUE');
      },

      );
  }
  
  update(a, b) {
    a.check = b.checked;
  }

}
