import { Component, OnInit, OnDestroy, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }           from '@angular/router';
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
import {Observable}                                                                         from 'rxjs/Rx'; 
import {Subscription}                                                                       from 'rxjs';


import { FileUploader }       from 'ng2-file-upload/ng2-file-upload';
//URL to use ng2-file-upload for generate the uploader array of files
const URL = 'https://evening-anchorage-3159.herokuapp.com/api/';

@Component({
  selector: 'sesion-2',
  templateUrl: 'sesion-2.component.html',
  animations: [
  ],
})



export class Sesion2Component implements OnInit, OnDestroy {

  paso = '0';
  cuerpoColegiadoSelect: CuerpoColegiado;
  cuerpoColegiadoSelectID = -1;

  otrosCuColegiado: CuerpoColegiadoSelect[];

  comentarioTema: string = ''
  comentarioTarea: string = ''

  actasCitadas: Acta[] = [];
  actaSelect: Acta = null;

  temasDelActa: Tema[] = [];
  temaActual: Tema = new Tema("", "", "", [], [], "", "");

  fechaMostrar = '';
  nuevaTareaSel = '';

  tareasMostrar: Tarea[] = [];

  tareaActual: Tarea = new Tarea("", "", "", [], null);

  indice = 0;
  indiceTAREA = 0;

  estadosUsuario = ["Presente", "Ausente", "Remoto"];

  estrategias = ["Estrategia1", "Estratagia2", "Estrategia3"];
  usuarios: Usuario[];


  showDialogAddCarre = false;
  showADD_TAREA = false;
  showADD_USUARIO = false;

  logo: string = "";

  queryString = "";


  public uploader:FileUploader = new FileUploader({url: URL});
  public hasBaseDropZoneOver:boolean = false;
  public show:boolean =true;
  public validatedTotal = 0;
  public loading: Subscription;
  public filesAnalyzed = [];

  start = new Date().getTime();

  temporizador = Observable.interval(1000).map(
  ()=> new Date().getTime() - this.start
  );


  constructor(private router: Router, private route: ActivatedRoute, private service: Service) {


  }


  cleanAll() {

    this.temasDelActa = [];
    this.start = new Date().getTime();


  }

  public onFileDrop(e:any) {
    let uploadedFiles = this.uploader.queue;
    this.show = false;

    if (confirm("Esta a punto de agregar un comentario. ¿Desea continuar?")) {

      uploadedFiles.forEach((fileItem) =>{
        let validatedFile = null;
        this.filesAnalyzed.push({'fileName': fileItem._file.name, 'analyzed': false});

        this.service.validateImage(fileItem._file).subscribe(
          response => {

            this.addComentarioDirecto(this.service.getServer().replace('metodologia-manager','').replace(' ','') + response.File.replace(' ',''))

          },
          error => {
          }
        );

      });
    }
  }

  ngOnInit(): void {
    this.logo = localStorage.getItem('logo');
    this.actaSelect = new Acta("", "", "", "", "", [], "", [], "", "", "", "", "", "", "", "", "", "", "","")

    let sub = this.route
      .queryParams
      .subscribe(

        result => {
          // Defaults to 0 if no query param provided.
          this.actaSelect.id = result['actaID']
        }

      );


    let ccID = this.actaSelect.id.split('-')[0].split('_')[1] + '-' + this.actaSelect.id.split('-')[1];

    this.service.getActa(this.actaSelect.id).subscribe(
      response => {
        this.actaSelect = response;
        this.paso = this.actaSelect.paso;
        console.log("Se establece el paso del acta: " + this.paso);


        this.service.getOtrosCuerposColegiado(ccID).subscribe(
          response => {
            this.otrosCuColegiado = response;

            for (let aa of this.otrosCuColegiado) {
              aa.check = false;
            }

          });


      });


  };

  ngOnDestroy(): void {

    alert("Se ha abandonado la sesión, se dejará constancia en el acta.")

  };


  indiceTemaMas() {
    if (this.indice < this.temasDelActa.length - 1) {
      this.indice = this.indice + 1;
      this.temaActual = this.temasDelActa[this.indice]
      this.updateTareas();
      console.log("tema ok")
      return true;
    }
    return false;

  }

  indiceTemaMenos() {
    if (this.indice > 0) {
      this.indice = this.indice - 1;
      this.temaActual = this.temasDelActa[this.indice]
      this.updateTareas();
      return true;
    }
    return false;


  }


  indiceTareaMas() {
    if (this.indiceTAREA < this.tareasMostrar.length - 1) {
      this.indiceTAREA = this.indiceTAREA + 1;
      this.tareaActual = this.tareasMostrar[this.indiceTAREA];
      console.log("tarea ok")
      return true;
    }
    return false;

  }

  indiceTareaMenos() {
    if (this.indiceTAREA > 0) {
      this.indiceTAREA = this.indiceTAREA - 1;
      this.tareaActual = this.tareasMostrar[this.indiceTAREA];
      return true;
    }
    return false;

  }


  integrantesConEstado() {

    let esta = false;
    for (let aa of this.actaSelect.integrantes) {
      if (!aa.estado)
        esta = true;
    }
    return esta;
  }

  removeUser(user): void {

    let nuevo: UsuarioActa[] = [];
    let asiiii: Usuario = user;

    let esta: boolean = false;
    for (let aa of this.actaSelect.integrantes) {
      if (aa.userID != asiiii.userID)
        nuevo.push(aa);
    }

    this.actaSelect.integrantes = nuevo;

  }


  integrantesPresentes(): UsuarioActa[] {

    let mm: UsuarioActa[] = [];

    if (!this.actaSelect)
      return mm;

    if (!this.actaSelect.integrantes)
      return mm;

    for (let aa of this.actaSelect.integrantes) {
      if (aa.estado == 'Presente' || aa.estado == 'Remoto')
        mm.push(aa)
    }
    return mm;
  }


  selectActa(actaCombo): void {
    this.actaSelect = this.actasCitadas[actaCombo.selectedIndex - 1];

  }

  update(a, b) {
    console.log("------------ METHOD_UPDATE [INI]-------------");
    console.log(a);
    console.log(b);
    console.log("-------------------------");
    a.check = b.checked;
    console.log(a);
    console.log(b);
    console.log("------------ METHOD_UPDATE [FIN]-------------");

  }

  clicActaNext(actaCombo): void {

    this.actaSelect = this.actasCitadas[actaCombo.selectedIndex - 1];

    this.paso = this.actaSelect.paso;
    if (+this.actaSelect.paso <= 0)
      this.updatePaso('1');


    this.service.getUsuariosConActa(this.actaSelect.id).subscribe(
      response => {
        this.usuarios = response;
      }
    );

    this.service.getTemas(this.actaSelect.id).subscribe(
      response => {
        this.temasDelActa = response;
        this.temasDelActa.sort((a, b) => {
          if ((+a.id) < (+b.id))
            return 1;
          else
          if ((+a.id) > (+b.id))
            return -1;
          else
            return 0;
        });
        if (response.length > 0) {
          this.temaActual = response[0];
          this.indice = 0;
        } else
          this.indice = -1;

        this.updateTareas();
      });


    let ccID = this.actaSelect.id.split('-')[0].split('_')[1] + '-' + this.actaSelect.id.split('-')[1];

    this.service.getOtrosCuerposColegiado(ccID).subscribe(
      response => {
        this.otrosCuColegiado = response;


        for (let aa of this.otrosCuColegiado) {
          aa.check = false;
        }

      });

  }

  addUser(user): void {

    let asiiii: Usuario = user;

    let esta: boolean = false;
    for (let aa of this.actaSelect.integrantes) {
      if (aa.userID == asiiii.userID)
        esta = true;
    }

    if (!esta) {
      this.actaSelect.integrantes.push(new UsuarioActa(asiiii.userID, asiiii.nombre, "", "", "",""));
    }
  }

  crearTema(tema, indicador, est, comm): void {

    let estrategia = this.estrategias[est.selectedIndex - 1];

    console.log("CREAR_TEMA:");
    console.log(this.otrosCuColegiado)
    console.log("Tema " + tema)

    let ccID = this.actaSelect.id.split('-')[0].split('_')[1] + '-' + this.actaSelect.id.split('-')[1];

    let temaN = new Tema("", "Abierto", tema, [], [], estrategia, indicador);

    let arreglo = [];

    for (let aa of this.otrosCuColegiado) {
      if (aa.check)
        arreglo.push(aa.id);
    }

    let loading =
      this.service.createTema(ccID, temaN, this.actaSelect.id, arreglo, comm).
    subscribe(
      response => {
        this.temasDelActa.unshift(response);

        this.temasDelActa.sort((a, b) => {
          if ((+a.id) < (+b.id))
            return 1;
          else
          if ((+a.id) > (+b.id))
            return -1;
          else
            return 0;
        });


        this.temaActual = response;
        this.updateTareas();

        alert("Se ha creado un nuevo tema")
      }
    );

  }

  crearTarea(tarea, responsable, fecha): void {

    console.log(responsable)
    console.log(responsable.selectedIndex)

    let aux: Usuario[] = this.integrantesPresentes();

    let respon: Usuario = aux[responsable.selectedIndex];


    let mmmm: UsuarioActa = new UsuarioActa(respon.userID, respon.nombre, "", "Presente", "","");

    let tareaN = new Tarea("", "Abierta", tarea, [], mmmm);

    let ccID = this.actaSelect.id.split('-')[0].split('_')[1] + '-' + this.actaSelect.id.split('-')[1];

    let loading =
      this.service.crearTarea(ccID, this.temaActual.id, tareaN).
    subscribe(
      response => {
        this.temaActual = response;

        if (this.temaActual.tareas.length > 0) {
          this.tareaActual = this.temaActual.tareas[0];
        } else {
          this.tareaActual = new Tarea("", "", "", [], null);
        }

        this.updateTareas();

        alert("Se ha creado un nueva tarea")
      }
    );

  }


  toStringTema(array) {

    let ff = '\n\n';

    for (let aa of array) {

      ff = ff + aa.texto + '\n'

    }

    return ff;
  }


  toString(array) {

    let ff = '\n';

    for (let aa of array) {

      ff = ff + aa + '\n'

    }

    return ff;
  }
  addComentario(com): void {

    if (confirm("Esta a punto de agregar un comentario. ¿Desea continuar?")) {
      this.service.createComentario(
        this.actaSelect.id.split('-')[0].split('_')[1] + '-' + this.actaSelect.id.split('-')[1], this.temaActual.id, com).subscribe(
        response => {
          this.temaActual = response;
        }
      );
    }
  }


  checkAvanzarTareas(): void {

    this.updatePaso('4');
    this.service.checkAvanzarTareasOK(this.actaSelect.id).subscribe(
      response => {}
    );

  }


  addComentarioTarea(com): void {

    if (confirm("Esta a punto de agregar un comentario. ¿Desea continuar?")) {
      this.service.createComentarioTarea(
        this.actaSelect.id.split('-')[0].split('_')[1] + '-' + this.actaSelect.id.split('-')[1], this.temaActual.id, this.tareaActual.id, com).subscribe(
        response => {
          this.tareaActual = response;
        }
      );
    }
  }

  updatePasoIntegrantes(paso): void {


    this.service.updateActaIntegrantes(
      this.actaSelect.id.split('-')[0].split('_')[1] + '-' + this.actaSelect.id.split('-')[1], this.actaSelect.id, this.actaSelect).subscribe(
      response => {
        this.actaSelect = response;

      });

    this.updatePaso(paso);
  }


  updatePaso(paso): void {

    this.service.updateActaPaso(
      this.actaSelect.id.split('-')[0].split('_')[1] + '-' + this.actaSelect.id.split('-')[1], this.actaSelect.id, paso).subscribe(
      response => {
        console.log(response);
        this.actaSelect = response;
        this.paso = paso;

      });

  }

  closeTarea(): void {

    if (confirm("Esta a punto de agregar un comentario. ¿Desea continuar?")) {

      this.service.closeTarea(
        this.actaSelect.id.split('-')[0].split('_')[1] + '-' + this.actaSelect.id.split('-')[1], this.temaActual.id,
        this.tareaActual.id,
        this.actaSelect.id).subscribe(
        response => {

          this.service.getTemas(this.actaSelect.id).subscribe(
            response => {
              this.temasDelActa = response;
              if (response.length > 0) {
                this.temaActual = response[0];
                this.indice = 0;
              } else
                this.indice = -1;

              this.updateTareas();
            });

        }
      );


    }


  }


  avanzar() {

    if (!this.indiceTareaMas())
      if (!this.indiceTemaMas())
        this.checkAvanzarTareas();

    this.start = new Date().getTime();

  }


  updateTareas() {
    this.tareasMostrar = [];

    for (let aa of this.temaActual.tareas) {
      if (aa.estado == "Abierto" || aa.estado == "Abierta")
        this.tareasMostrar.push(aa);
    }

    this.tareasMostrar.sort((a, b) => {
      if ((+a.id) < (+b.id))
        return 1;
      else
      if ((+a.id) > (+b.id))
        return -1;
      else
        return 0;
    });


    if (this.tareasMostrar.length > 0) {
      this.tareaActual = this.tareasMostrar[0];
      this.indiceTAREA = 0;


    } else {
      this.tareaActual = new Tarea("", "", "", [], null);
      this.indiceTAREA = -1;
    }

  }


  closeActa() {
    alert('Se enviará un email con las minutas del acta y el resumen por PDF de la misma');

                this.service.closeActa(this.actaSelect.id.split('-')[0].split('_')[1] + '-' + this.actaSelect.id.split('-')[1], this.actaSelect.id, this.actaSelect).subscribe(
              response => {


              });

  }


  esDone() : boolean {

    return  (this.actaSelect.seCumpliofinEnMente+"") == "" || 
            (this.actaSelect.elTiempoFueSuficiente+"") == "" || 

            (this.actaSelect.huboInconvenientes+"") == "" ||  
            (this.actaSelect.huboInconvenientes+"" == "true"  && this.actaSelect.huboInconvenientesTexto+"" == "")  || 

            (this.actaSelect.tieneSugerencias+"") == "" || 
            (this.actaSelect.tieneSugerencias+"" == "true"  && this.actaSelect.tieneSugerenciasTexto+"" == "")  || 

            (this.actaSelect.redaccionDeTareasOk+"") == "" ;


  }


  addComentarioDirecto(com): void {

      this.service.createComentario(
        this.actaSelect.id.split('-')[0].split('_')[1] + '-' + this.actaSelect.id.split('-')[1], this.temaActual.id, this.actaSelect.id + '___' + com).subscribe(
        response => {
          this.temaActual = response;
        }
        );
      }

  closeTema(com): void {

    let tareasAbiertas: boolean = false;
    for (let aa of this.temaActual.tareas) {
      if (aa.estado == 'Abierto')
        tareasAbiertas = true;
    }


    if (tareasAbiertas)
      alert("No se puede cerrar porque existen tareas abiertas.")
    else {
      if (confirm("Esta a punto de cerrar un tema. ¿Desea continuar?")) {
        this.service.closeTema(
          this.actaSelect.id.split('-')[0].split('_')[1] + '-' + this.actaSelect.id.split('-')[1], this.temaActual.id, com).subscribe(
          response => {

            this.service.getTemas(this.actaSelect.id).subscribe(
              response => {
                this.temasDelActa = response;
                if (response.length > 0) {
                  this.temaActual = response[0];
                  this.indice = 0;
                } else
                  this.indice = -1;

                this.updateTareas();
              });

          }
        );
      }


    }


  }
}

