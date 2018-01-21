import { Component, OnInit, OnDestroy, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
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
  selector: 'change-password',
  templateUrl: 'change-password.component.html',
  animations: [
  ],
})

export class ChangePasswordComponent implements OnInit, OnDestroy  {

  visiblePop : boolean = false;
  cuerposColegiado: CuerpoColegiadoSelect[];
  empresaCreada : string = '';


  constructor( 
    private router: Router, 
    private route : ActivatedRoute, 
    private service: Service)
  {

    this.cuerposColegiado = [];

  }

  quitarToken(){
      localStorage.setItem('empresa-creada','');
  }

  ngOnDestroy(): void {
    this.quitarToken();
  }

  ngOnInit(): void {

    this.empresaCreada = localStorage.getItem('empresa-creada');



    this.service.getCuerpoColegiados().subscribe(
      response =>{ 

        for (let cc of response) {
          this.cuerposColegiado.push(new CuerpoColegiadoSelect(cc.id, cc.nombre, cc.actas));
        }
      });

  };

  isValid( a, b) : boolean{

    return (a.length > 3) && a == b


  }


  changePassword(passActual, passNueva, passNuevaConf):void {

    this.service.changePasswordUsuario(passActual, passNueva, passNuevaConf).subscribe(
      response =>{ 
        alert("Se ha modificado la clave exitosamente. Ingrese con su nueva clave.")
        this.logout();
      },
      error =>{ 
        alert("Ha ocurrido un error durante el cambio de contraseña.")
      },

      );
  }
  
  update(a, b) {
    a.check = b.checked;
  }


  logout(){
    localStorage.clear();
    this.router.navigate(['/login'])    
  }

}
