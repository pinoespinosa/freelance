import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';
import { Service }   			      						      from 'app/service';
import { Client }               									from 'app/data-objects/cliente';
import { Trabajo }               									from 'app/data-objects/trabajo';
import { CuerpoColegiado }               					from 'app/data-objects/cuerpoColegiado';
import { Acta }               										from 'app/data-objects/acta';
import { Tema }                                   from 'app/data-objects/tema';
import { Tarea }                                   from 'app/data-objects/tarea';

import { Usuario }                                from 'app/data-objects/usuario';
import { UsuarioActa }                                  from 'app/data-objects/usuarioActa';


@Component({
  selector: 'sesion',
  templateUrl: 'sesion.component.html',
  animations: [
  ],
})

export class SesionComponent implements OnInit  {

  paso = 0 ;
	cuerpoColegiadoSelect: CuerpoColegiado;
 	cuerpoColegiadoSelectID = -1;

  actasCitadas: Acta[] = [];
  actaSelect: Acta = null;

  temasDelActa: Tema[] = [];
  temaActual: Tema = new Tema("","","",[],[]);
  

  tareasMostrar: Tarea[] = [];

  tareaActual: Tarea = new Tarea("","","",[]);

  tareasFiltro = "Todas";

  indice = 0;
  indiceTAREA = 0;

  estadosUsuario = ["Presente", "Ausente", "Remoto"];

  usuarios : Usuario[];


  showDialogAddCarre=false;

  showADD_TAREA=false;

  logo:string = "";






  constructor(    private router: Router, private route : ActivatedRoute, private service: Service
){

  this.logo = localStorage.getItem('logo');

    let sub = this.route
      .queryParams
      .subscribe(

        result => {
          // Defaults to 0 if no query param provided.
          this.cuerpoColegiadoSelectID = result['cc']

        }

      );

		let loading = this.service.getActasCitadas().subscribe(
      		response =>{ 
        		this.actasCitadas = response;
      		});




}

	ngOnInit(): void {



	};

  indiceTemaMas(){
    if (this.indice < this.temasDelActa.length -1){
      this.indice = this.indice +1;
      this.temaActual = this.temasDelActa[this.indice]
    }
    this.updateTareas();

  }

  indiceTemaMenos(){
    if (this.indice > 0 ){
      this.indice = this.indice -1;
      this.temaActual = this.temasDelActa[this.indice]
    }

    this.updateTareas();

  }



  indiceTareaMas(){
    if (this.indiceTAREA < this.tareasMostrar.length -1){
      this.indiceTAREA = this.indiceTAREA +1;
      this.tareaActual = this.tareasMostrar[this.indiceTAREA];
    }
  }

  indiceTareaMenos(){
    if (this.indiceTAREA > 0 ){
        this.indiceTAREA = this.indiceTAREA -1;
        this.tareaActual = this.tareasMostrar[this.indiceTAREA];
    }
  }

selectActa(actaCombo):void{
      this.actaSelect = this.actasCitadas[actaCombo.selectedIndex-1];
      this.updateTareas();

}

clicActaNext(actaCombo):void{
      this.actaSelect = this.actasCitadas[actaCombo.selectedIndex-1];
      this.paso = 1;

    this.service.getUsuariosConActa(this.actaSelect.id).subscribe(
      response =>{ 
        this.usuarios = response;
      }         
    );

    this.service.getTemas(this.actaSelect.id).subscribe(
      response =>{ 
        this.temasDelActa = response;
        this.temasDelActa.sort((a, b) => {
        if ( (+a.id) < (+b.id) ) 
          return 1;
        else 
          if ((+a.id) > (+b.id) ) 
            return -1 ;
          else 
            return 0;
    });
        if(response.length>0){
          this.temaActual = response[0];
          this.indice=0;
        }
        else
          this.indice=-1;

        this.updateTareas();
      });

        
}

	addUser(user):void{

  console.log(this.actaSelect.integrantes[user.selectedIndex-1])
  let asiiii : Usuario =  this.usuarios[user.selectedIndex-1];

  let esta:boolean = false;
  for (let aa of this.actaSelect.integrantes) {
    if (aa.userID == asiiii.userID)
      esta = true;
  }
  
  if (!esta){
    this.actaSelect.integrantes.push(new UsuarioActa(asiiii.userID, asiiii.nombre, ""));
  }
}

  crearTema(tema):void{

    console.log("Tema " + tema)
    let temaN = new Tema("","Abierto",tema,[],[]);
    let ccID = this.actaSelect.id.split('-')[0].split('_')[1]+'-'+this.actaSelect.id.split('-')[1];

    let loading = 
    this.service.createTema(ccID, temaN,this.actaSelect.id).
    subscribe(
      response =>{ 
        this.temasDelActa.unshift(response);

        this.temasDelActa.sort((a, b) => {
        if ( (+a.id) < (+b.id) ) 
          return 1;
        else 
          if ((+a.id) > (+b.id) ) 
            return -1 ;
          else 
            return 0;
    });


        this.temaActual=response;
        alert("Se ha creado un nuevo tema")
      }         
    );

  }

  crearTarea(tarea):void{

    let tareaN = new Tarea("","Abierto",tarea,[]);

    let ccID = this.actaSelect.id.split('-')[0].split('_')[1]+'-'+this.actaSelect.id.split('-')[1];

    let loading = 
    this.service.crearTarea(ccID,this.temaActual.id, tareaN).
    subscribe(
      response =>{ 
        this.temaActual=response;

        if (this.temaActual.tareas.length > 0){
          this.tareaActual = this.temaActual.tareas[0];
        } 
        else{
          this.tareaActual = new Tarea("","","",[]);
        }

        alert("Se ha creado un nueva tarea")
      }         
    );

  }


  toString(array){

  let ff='\n\n';

  for (let aa of array) {

  ff = ff + aa + '\n'

  }

    return ff;
  }

  addComentario(com):void{

    if (confirm("Esta a punto de agregar un comentario. ¿Desea continuar?")){
      this.service.createComentario(
        this.actaSelect.id.split('-')[0].split('_')[1]+'-'+this.actaSelect.id.split('-')[1], this.temaActual.id, com).subscribe(
          response =>{ 
            this.temaActual = response;
          }         
        );
    }
  }


  checkAvanzarTareas():void{

      this.service.checkAvanzarTareas(this.actaSelect.id).subscribe(
          response =>{ 
            if (confirm(response.detalle)){
              this.paso=4;

            }
          }         
        );
    
  }


  addComentarioTarea(com):void{

    if (confirm("Esta a punto de agregar un comentario. ¿Desea continuar?")){
      this.service.createComentarioTarea(
        this.actaSelect.id.split('-')[0].split('_')[1]+'-'+this.actaSelect.id.split('-')[1], this.temaActual.id, this.tareaActual.id, com).subscribe(
          response =>{ 
            this.tareaActual = response;
          }         
        );
    }
  }


  closeTarea():void{

    if (confirm("Esta a punto de agregar un comentario. ¿Desea continuar?")){
      this.service.closeTarea(
        this.actaSelect.id.split('-')[0].split('_')[1]+'-'+this.actaSelect.id.split('-')[1], this.temaActual.id, this.tareaActual.id, this.actaSelect.id).subscribe(
          response =>{ 
            this.tareaActual = response;
          }         
        );

    this.service.getTemas(this.actaSelect.id).subscribe(
      response =>{ 
        this.temasDelActa = response;
        if(response.length>0){
          this.temaActual = response[0];
          this.indice=0;
        }
        else
          this.indice=-1;

        this.updateTareas();
      });








        
    }



  }

  updateTareas(){
    this.tareasMostrar = [];
    for (let aa of this.temaActual.tareas) {

      if (this.tareasFiltro=="Todas")
        this.tareasMostrar.push(aa);
      else{
        if (aa.estado == this.tareasFiltro)
          this.tareasMostrar.push(aa);
        }
    }

        this.tareasMostrar.sort((a, b) => {
        if ( (+a.id) < (+b.id) ) 
          return 1;
        else 
          if ((+a.id) > (+b.id) ) 
            return -1 ;
          else 
            return 0;
    });


    if (this.tareasMostrar.length > 0){
      this.tareaActual = this.tareasMostrar[0];
      this.indiceTAREA = 0;


    } 
    else{
      this.tareaActual = new Tarea("","","",[]);
      this.indiceTAREA = -1;
    }

  }

  updateFiltro(valor){

    this.tareasFiltro = valor;
    this.updateTareas();

  }

  closeActa(com):void{

    let tareasAbiertas : boolean = false;
    for (let aa of this.temaActual.tareas) {
        if (aa.estado == 'Abierto')
          tareasAbiertas = true;
        }
    

    if (tareasAbiertas)
        alert("No se puede cerrar porque existen tareas abiertas.")
    else
{
    if (confirm("Esta a punto de cerrar un tema. ¿Desea continuar?")){
      this.service.closeActa(
        this.actaSelect.id.split('-')[0].split('_')[1]+'-'+this.actaSelect.id.split('-')[1], this.temaActual.id, com).subscribe(
          response =>{ 



          }         
        );
    }

        this.service.getTemas(this.actaSelect.id).subscribe(
      response =>{ 
        this.temasDelActa = response;
        if(response.length>0){
          this.temaActual = response[0];
          this.indice=0;
        }
        else
          this.indice=-1;

        this.updateTareas();
      });
  }

	
}
 }