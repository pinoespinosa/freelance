import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';
import { Service }                                      from 'app/service';
import { Client }                                       from 'app/data-objects/cliente';
import { Trabajo }                                      from 'app/data-objects/trabajo';
import { FileUploader }       from 'ng2-file-upload/ng2-file-upload';

import { CuerpoColegiado }                              from 'app/data-objects/cuerpoColegiado';
import { CuerpoColegiadoSelect }                  from 'app/data-objects/cuerpoColegiadoSelect';
import { Acta }                                         from 'app/data-objects/acta';
import { UsuarioActa }                                  from 'app/data-objects/usuarioActa';
import { Usuario }                                      from 'app/data-objects/usuario';

//URL to use ng2-file-upload for generate the uploader array of files
const URL = 'https://evening-anchorage-3159.herokuapp.com/api/';

@Component({
  selector: 'create-empresa',
  templateUrl: 'create-empresa.component.html',
  animations: [
  ],
})

export class CreateEmpresaComponent implements OnInit  {

  public uploader:FileUploader = new FileUploader({url: URL});
  public hasBaseDropZoneOver:boolean = false;
  public filesAnalyzed = [];
  public show:boolean =true;
  public validatedTotal = 0;


  titulo : string = 'Crear Empresa'

  visiblePop : boolean = false;

  modoCreacion : boolean = false;

  nombreAux : string = ''
  logoAux : string = ''

  cuerposColegiado: CuerpoColegiadoSelect[];
  empresas: any[];

  imagen: string =""


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


  public fileOverBase(e:any):void {
    this.hasBaseDropZoneOver = e;
  }

  public allFilesAnalyzed() :any {
    let ret = true;  
    this.filesAnalyzed.forEach( (file) => {
      ret = ret && file.analyzed;
    });
    return ret;
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

            this.imagen = response.File.replace(' ','')

          },
          error => {
          }
        );

      });
    }
  }

  createEmpresa(nombre, logo):void{

    console.log(nombre)

    let emp = {
      'id': '',
      'nombreEmpresa' : nombre,
      'logoEmpresa' : this.imagen
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
