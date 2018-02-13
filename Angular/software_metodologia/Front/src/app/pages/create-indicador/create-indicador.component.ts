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
selector: 'create-indicador',
templateUrl: 'create-indicador.component.html',
animations: [
],
})

export class CreateIndicadorComponent implements OnInit  {

titulo : string = 'Crear'

visiblePop : boolean = false;

modoCreacion : boolean = false;

nombreAux : string = ''
logoAux : string = ''

empresaCreada = ''

codIndicador = ''
tituloIndicador = ''


constructor( private router: Router, private route : ActivatedRoute, private service: Service)
{

}

ngOnInit(): void {
};

temporizador = Observable.interval(2000).map(
()=> this.refresh()
);

ngOnDestroy(): void {
}

  refreshAll(){

    let val = localStorage.getItem('empresaID')
    this.service.getEstrategias(val).subscribe(
    response =>{ 
      this.empresaCreada = localStorage.getItem('empresa-creada');
  });
}

  refresh():boolean{
    if (this.empresaCreada != localStorage.getItem('empresa-creada')){
      this.refreshAll();
    }
    return true;
  }

  createIndicador(indicador){

    let val = localStorage.getItem('empresaID')
    this.service.createIndicador(val, indicador).subscribe(
    response =>{ 
      alert('Se ha creado un nuevo indicador')
  });
}

  deleteEstrategia(estrategia){

    let val = localStorage.getItem('empresa-creada')
    this.service.deleteEstrategia(val, estrategia).subscribe(
    response =>{ 
      this.empresaCreada = localStorage.getItem('empresa-creada');
  });
}

}

