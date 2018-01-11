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
import { ReunionesComponent }               from './pages/reuniones/reuniones.component';

import { CreateUserComponent }              from './pages/create-user/create-user.component';
import { CreateEmpresaComponent }           from './pages/create-empresa/create-empresa.component';

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


import { FormsModule, ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    LoginComponent,
    HeaderComponent,
    AppComponent,

    HomeComponent,
    ConsultasComponent,
    ActaCompletaComponent,
    ReunionesComponent,
    SesionComponent,
    Sesion2Component,
    FilterPipe,
    ChangeStateDialogComponent,
    MantenimientoComponent,

    CreateUserComponent,
    CreateEmpresaComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    ChartsModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule, 
    FormsModule
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
