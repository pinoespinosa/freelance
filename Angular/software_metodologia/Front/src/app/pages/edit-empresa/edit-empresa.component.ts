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
  selector: 'edit-empresa',
  templateUrl: 'edit-empresa.component.html',
  animations: [
  ],
})

export class EditEmpresaComponent implements OnInit  {

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
        this.router.navigate(['/home']);
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
  
  update(a, b) {
    a.check = b.checked;
  }

}
