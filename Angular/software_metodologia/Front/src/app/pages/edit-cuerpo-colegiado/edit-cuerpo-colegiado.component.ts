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
  selector: 'edit-cuerpo-colegiado',
  templateUrl: 'edit-cuerpo-colegiado.component.html',
  animations: [
  ],
})

export class EditCuerpoColegiadoComponent implements OnInit  {

  titulo : string = 'Editar Cuerpo Colegiado'

  visiblePop : boolean = false;

  modoCreacion : boolean = false;

  nombreAux : string = ''
  prefijo : string = ''

  cuerposColegiadoSelect: any = null;
  empresaSelect: any = null;

  cuerpos: any[];
  empresas: any[];


  constructor( 
    private router: Router, 
    private route : ActivatedRoute, 
    private service: Service)
  {


  }

  ngOnInit(): void {
    this.refreshEmpresas();
  };

  createEmpresa(nombre, logo):void{

    console.log(nombre)

    let emp = {
      'id': '',
      'nombreEmpresa' : nombre,
      'logoEmpresa' : logo
    }

    this.service.createEmpresa(emp).subscribe(
      response =>{ 
        alert("Se ha creado la empresa exitosamente.")
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

      if (this.empresaSelect != null){
        this.service.getCuerpoColegiadosSimple(this.empresaSelect).subscribe(
          response =>{ 
            this.cuerpos = response;
          });
      }
  }


  
  updatte(aa) {
    this.empresaSelect = aa;
            this.service.getCuerpoColegiadosSimple(this.empresaSelect).subscribe(
          response =>{ 
            this.cuerpos = response;
          });
  }

  updatteCuerpo(aa) {
    this.cuerposColegiadoSelect = aa;
    this.nombreAux = aa.nombre;
    this.prefijo = aa.prefijoDocs;
  }

}
