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
import { FileUploader }       from 'ng2-file-upload/ng2-file-upload';

//URL to use ng2-file-upload for generate the uploader array of files
const URL = 'https://evening-anchorage-3159.herokuapp.com/api/';


@Component({
  selector: 'edit-empresa',
  templateUrl: 'edit-empresa.component.html',
  animations: [
  ],
})

export class EditEmpresaComponent implements OnInit  {

  public uploader:FileUploader = new FileUploader({url: URL});
  public hasBaseDropZoneOver:boolean = false;
  public filesAnalyzed = [];
  public show:boolean =true;
  public validatedTotal = 0;

  titulo : string = 'Administrar Empresas'

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




  ngOnInit(): void {
    this.refreshEmpresas();
  };

  updateEmpresa(id, nombre):void{

    console.log(nombre)

    let emp = {
      'id': id,
      'nombreEmpresa' : nombre,
      'logoEmpresa' : this.imagen
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

  setEmpresaLocalSto(empres){
    localStorage.setItem('empresa-creada',empres.id);
  }

}
