import { Component, OnInit, OnDestroy, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute, NavigationExtras }           from '@angular/router';
import { trigger, state, style, animate, transition }      from '@angular/animations';
import { Service }   			      						      from 'app/service';
import { Client }               									from 'app/data-objects/cliente';
import { Trabajo }               									from 'app/data-objects/trabajo';
import { CuerpoColegiado }               					from 'app/data-objects/cuerpoColegiado';
import { CuerpoColegiadoSelect }                  from 'app/data-objects/cuerpoColegiadoSelect';
import { Acta }               										from 'app/data-objects/acta';
import { Tema }                                   from 'app/data-objects/tema';
import { Tarea }                                  from 'app/data-objects/tarea';

import { Usuario }                                from 'app/data-objects/usuario';
import { UsuarioActa }                            from 'app/data-objects/usuarioActa';



@Component({
  selector: 'sesion',
  templateUrl: 'sesion.component.html',
  animations: [
  ],
})


export class SesionComponent implements OnInit, OnDestroy  {

  paso = '0' ;
	cuerpoColegiadoSelect: CuerpoColegiado;
 	cuerpoColegiadoSelectID = -1;

  otrosCuColegiado: CuerpoColegiadoSelect[];

  comentarioTema : string = ''
  comentarioTarea : string = ''

  actasCitadas: Acta[] = [];
  actaSelect: Acta = null;

  temasDelActa: Tema[] = [];
  temaActual: Tema = new Tema("","","",[],[],"","");
  
  fechaMostrar = '';
  nuevaTareaSel ='';

  tareasMostrar: Tarea[] = [];

  tareaActual: Tarea = new Tarea("","","",[],null);

  tareasFiltro = "Todas";

  indice = 0;
  indiceTAREA = 0;

  estadosUsuario = ["Presente", "Ausente", "Remoto"];

  estrategias = ["Estrategia1", "Estratagia2", "Estrategia3"];
  usuarios : Usuario[];


  showDialogAddCarre=false;
  showADD_TAREA=false;
  showADD_USUARIO=false;

  logo:string = "";

queryString="";




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

  ngOnDestroy(): void {
  };


  indiceTemaMas(){
    if (this.indice < this.temasDelActa.length -1){
      this.indice = this.indice +1;
      this.temaActual = this.temasDelActa[this.indice]
      this.updateTareas();
      console.log("tema ok")
      return true;
    }
    return false;

  }

  indiceTemaMenos(){
    if (this.indice > 0 ){
      this.indice = this.indice -1;
      this.temaActual = this.temasDelActa[this.indice]
      this.updateTareas();
      return true;
    }
    return false;


  }


  indiceTareaMas(){
    if (this.indiceTAREA < this.tareasMostrar.length -1){
      this.indiceTAREA = this.indiceTAREA +1;
      this.tareaActual = this.tareasMostrar[this.indiceTAREA];
      console.log("tarea ok")
       return true;
    }
    return false;

  }

  indiceTareaMenos(){
    if (this.indiceTAREA > 0 ){
        this.indiceTAREA = this.indiceTAREA -1;
        this.tareaActual = this.tareasMostrar[this.indiceTAREA];
        return true;
    }
    return false;

  }




  integrantesConEstado(){
  
    let esta = false;
    for (let aa of this.actaSelect.integrantes) {
      if (!aa.estado)
        esta = true;
    }
    return esta;
  }

removeUser(user):void{

  let nuevo: UsuarioActa[]=[];
  let asiiii : Usuario =  user;

  let esta:boolean = false;
  for (let aa of this.actaSelect.integrantes) {
    if (aa.userID != asiiii.userID)
      nuevo.push(aa);
  }
  
  this.actaSelect.integrantes = nuevo;

}


 integrantesPresentes():UsuarioActa[]{
  
  let mm : UsuarioActa[] = []; 

  if (!this.actaSelect)
    return mm;

  if (!this.actaSelect.integrantes)
    return mm;

  for (let aa of this.actaSelect.integrantes) {
    if (aa.estado == 'Presente')
      mm.push(aa)
  }
  return mm;
}




selectActa(actaCombo):void{
      this.actaSelect = this.actasCitadas[actaCombo.selectedIndex-1];

}

update(a,b){
a.check = b.checked;
}

clicActaNext(actaCombo):void{

      this.actaSelect = this.actasCitadas[actaCombo.selectedIndex-1];
      
      this.paso = this.actaSelect.paso;
      if ( +this.actaSelect.paso <= 0)
        this.updatePaso('1');


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

    
      let ccID = this.actaSelect.id.split('-')[0].split('_')[1]+'-'+this.actaSelect.id.split('-')[1];

    this.service.getOtrosCuerposColegiado(ccID).subscribe(
          response =>{ 
            this.otrosCuColegiado = response;



  for (let aa of this.otrosCuColegiado) {
    aa.check = false;
  }

          });

}

	addUser(user):void{

  let asiiii : Usuario =  user;

  let esta:boolean = false;
  for (let aa of this.actaSelect.integrantes) {
    if (aa.userID == asiiii.userID)
      esta = true;
  }
  
  if (!esta){
    this.actaSelect.integrantes.push(new UsuarioActa(asiiii.userID, asiiii.nombre, "","",""));
  }
}

  crearTema(tema, indicador, est):void{

    let estrategia = this.estrategias[est.selectedIndex-1];

    console.log("Tema " + tema)
    console.log(this.otrosCuColegiado)

    let temaN = new Tema("","Abierto",tema,[],[],estrategia, indicador);
    let ccID = this.actaSelect.id.split('-')[0].split('_')[1]+'-'+this.actaSelect.id.split('-')[1];


    let arreglo = [];

    for (let aa of this.otrosCuColegiado) {
      arreglo.push(aa.id);
    }



    let loading = 
    this.service.createTema(ccID, temaN,this.actaSelect.id, arreglo).
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

  crearTarea(tarea, responsable, fecha):void{

    console.log(responsable)
    console.log(responsable.selectedIndex)

    let aux : Usuario[] = this.integrantesPresentes();

    let respon : Usuario =  aux[responsable.selectedIndex];



    let mmmm : UsuarioActa = new UsuarioActa(respon.userID, respon.nombre, "","Presente","");

    let tareaN = new Tarea("","Abierto",tarea,[],mmmm);

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
          this.tareaActual = new Tarea("","","",[],null);
        }

        this.updateTareas();

        alert("Se ha creado un nueva tarea")
      }         
    );

  }


  toStringTema(array){

  let ff='\n\n';

  for (let aa of array) {

  ff = ff + aa.texto + '\n'

  }

    return ff;
  }


  toString(array){

  let ff='\n';

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

    let navigationExtras: NavigationExtras = {
        queryParams: {
            "actaID": this.actaSelect.id
        }
    };
    this.updatePaso('4');

    this.router.navigate(['/sesion-2'],navigationExtras);
    
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

  updatePasoIntegrantes(paso):void{


    this.service.updateActaIntegrantes(
        this.actaSelect.id.split('-')[0].split('_')[1]+'-'+this.actaSelect.id.split('-')[1], this.actaSelect.id, this.actaSelect).subscribe(
      response =>{ 
          console.log(response);
          this.actaSelect = response;

        });

    this.updatePaso(paso);
  }


  updatePaso(paso):void{

    this.service.updateActaPaso(
        this.actaSelect.id.split('-')[0].split('_')[1]+'-'+this.actaSelect.id.split('-')[1], this.actaSelect.id, paso).subscribe(
      response =>{ 
          console.log(response);
          this.actaSelect = response;
          this.paso = paso;

        });

  }

  closeTarea():void{

    if (confirm("Esta a punto de agregar un comentario. ¿Desea continuar?")){
      
      this.service.closeTarea(
        this.actaSelect.id.split('-')[0].split('_')[1]+'-'+this.actaSelect.id.split('-')[1], this.temaActual.id, 
        this.tareaActual.id, 
        this.actaSelect.id).subscribe(
          response =>{ 

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
        );

    
    }



  }


  avanzar(){

    if (!this.indiceTareaMas())
      if (!this.indiceTemaMas())
        this.checkAvanzarTareas();

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
      this.tareaActual = new Tarea("","","",[],null);
      this.indiceTAREA = -1;
    }

  }

  updateFiltro(valor){

    this.tareasFiltro = valor;
    this.updateTareas();

  }

  closeActa(){


  alert('Se enviará un email con las minutas del acta y el resumen por PDF de la misma');
  }


  closeTema(com):void{

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
      this.service.closeTema(
        this.actaSelect.id.split('-')[0].split('_')[1]+'-'+this.actaSelect.id.split('-')[1], this.temaActual.id, com).subscribe(
          response =>{ 

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
        );
    }


  }

	
}
 }