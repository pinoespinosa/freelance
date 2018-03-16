// ######################## Dependencias de Angular ########################
import { BrowserModule }                    from '@angular/platform-browser';
import { BrowserAnimationsModule }          from '@angular/platform-browser/animations';
import { NgModule }                         from '@angular/core';
import { HttpClientModule }                 from '@angular/common/http';
import { HttpModule }                       from '@angular/http';
import { ChartsModule }                     from 'ng2-charts';

// ######################## Manejo de permisos y rutas ########################
import { AuthGuard }                        from './routing/auth-guard.service';
import { CanDeactivateGuard }                        from './routing/CanDeactivateGuard';

import { PaymentAuthGuard }                 from './routing/payment-auth-guard.service';
import { AppRoutingModule }                 from './routing/app-routing.module';

// ######################## Paginas del sitio ########################
import { LoginComponent }                   from './pages/login/login.component';
import { ConsultasComponent }               from './pages/consultas/consultas.component';

import { ConsultasTemaComponent }           from './pages/consultas/consultas-tema/consultas-tema.component';
import { ConsultasTareaComponent }          from './pages/consultas/consultas-tarea/consultas-tarea.component';
import { ConsultasFinMenteComponent }       from './pages/consultas/consultas-fin-mente/consultas-fin-mente.component';
import { ConsultasActaComponent }           from './pages/consultas/consultas-acta/consultas-acta.component';

import { VisorActaComponent }               from './pages/consultas/visor-acta/visor-acta.component';

import { ReunionesComponent }               from './pages/reuniones/reuniones.component';
import { ReunionesEdicionComponent }        from './pages/reuniones-edicion/reuniones-edicion.component';

import { CreateUserComponent }              from './pages/create-user/create-user.component';
import { CreateEmpresaComponent }           from './pages/create-empresa/create-empresa.component';
import { EditEmpresaComponent }             from './pages/edit-empresa/edit-empresa.component';
import { CreateCuerpoColegiadoComponent }   from './pages/create-cuerpo-colegiado/create-cuerpo-colegiado.component';
import { EditCuerpoColegiadoComponent }     from './pages/edit-cuerpo-colegiado/edit-cuerpo-colegiado.component';

import { CreateEstrategiaComponent }        from './pages/create-estrategia/create-estrategia.component';
import { CreateIndicadorComponent }         from './pages/create-indicador/create-indicador.component';

import { ChangePasswordComponent }          from './pages/change-password/change-password.component';

import { MantenimientoComponent }           from './pages/mantenimiento/mantenimiento.component';

import { SesionComponent }                  from './pages/sesion/sesion.component';
import { Sesion2Component }                 from './pages/sesion-2/sesion-2.component';

import { ActaCompletaComponent }            from './pages/acta-completa/acta-completa.component';
import { ChangeStateDialogComponent }       from './pages/change-state-dialog/change-state-dialog.component';


import { HeaderComponent }                  from './pages/header/header.component';
import { HomeComponent }                    from './pages/home/home.component';

import { AppComponent }                     from './app.component';
import { Service }                          from './service';

import { FilterPipe }                       from './pages/pipe/pipe';
import { FilterPipe2 }                       from './pages/pipe2/pipe';

import { FileUploadModule }                   from 'ng2-file-upload/ng2-file-upload';


import { FormsModule, ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    LoginComponent,
    HeaderComponent,
    AppComponent,

    HomeComponent,
    ConsultasComponent,
    ConsultasTemaComponent,
    ConsultasTareaComponent,
    ConsultasFinMenteComponent,
    ConsultasActaComponent,
    VisorActaComponent,

    ActaCompletaComponent,
    ReunionesComponent,
    ReunionesEdicionComponent,
    SesionComponent,
    Sesion2Component,
    FilterPipe,
    FilterPipe2,
    ChangeStateDialogComponent,
    MantenimientoComponent,

    CreateUserComponent,
    CreateEmpresaComponent,
    EditEmpresaComponent,
    CreateCuerpoColegiadoComponent,
    EditCuerpoColegiadoComponent,
    CreateIndicadorComponent,
    CreateEstrategiaComponent,
    ChangePasswordComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    ChartsModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule, 
    FormsModule,
    ReactiveFormsModule,
    FileUploadModule,

  ],
  providers: [ 
    Service,
    AuthGuard,
    PaymentAuthGuard,
    CanDeactivateGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
