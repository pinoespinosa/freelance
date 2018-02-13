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

import {Observable}                                     from 'rxjs/Rx'; 
import {Subscription}                                   from 'rxjs';

@Component({
selector: 'create-estrategia',
templateUrl: 'create-estrategia.component.html',
animations: [
],
})

export class CreateEstrategiaComponent implements OnInit  {

titulo : string = 'Admin Estrategias'

visiblePop : boolean = false;

modoCreacion : boolean = false;

nombreAux : string = ''
logoAux : string = ''

estrategias: any[];
empresaCreada = ''


constructor( private router: Router, private route : ActivatedRoute, private service: Service)
{

}

ngOnInit(): void {
  this.refreshAll();
};

temporizador = Observable.interval(2000).map(
()=> this.refresh()
);

ngOnDestroy(): void {
}

  refreshAll(){

    let val = localStorage.getItem('empresa-creada')
    this.service.getEstrategias(val).subscribe(
    response =>{ 
      this.estrategias = response
      this.empresaCreada = localStorage.getItem('empresa-creada');
  });
}

  refresh():boolean{
    if (this.empresaCreada != localStorage.getItem('empresa-creada')){
      this.refreshAll();
    }
    return true;
  }

  createEstrategia(estrategia){

    let val = localStorage.getItem('empresa-creada')
    this.service.createEstrategia(val, estrategia).subscribe(
    response =>{ 
      this.estrategias = response
      this.empresaCreada = localStorage.getItem('empresa-creada');
  });
}

  deleteEstrategia(estrategia){

    let val = localStorage.getItem('empresa-creada')
    this.service.deleteEstrategia(val, estrategia).subscribe(
    response =>{ 
      this.estrategias = response
      this.empresaCreada = localStorage.getItem('empresa-creada');
  });
}

}

