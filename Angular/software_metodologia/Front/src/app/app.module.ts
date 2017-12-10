// ######################## Dependencias de Angular ########################
import { BrowserModule }                    from '@angular/platform-browser';
import { BrowserAnimationsModule }          from '@angular/platform-browser/animations';
import { NgModule }                         from '@angular/core';
import { HttpClientModule }                 from '@angular/common/http';
import { HttpModule }                       from '@angular/http';
import { ChartsModule }                     from 'ng2-charts';

// ######################## Manejode permisos y rutas ########################
import { AuthGuard }                        from './routing/auth-guard.service';
import { PaymentAuthGuard }                 from './routing/payment-auth-guard.service';
import { AppRoutingModule }                 from './routing/app-routing.module';

// ######################## Paginas del sitio ########################
import { LoginComponent }                   from './pages/login/login.component';
import { ConsultasComponent }               from './pages/consultas/consultas.component';
import { ReunionesComponent }               from './pages/reuniones/reuniones.component';
import { SesionComponent }               from './pages/sesion/sesion.component';

import { ActaCompletaComponent }            from './pages/acta-completa/acta-completa.component';
import { ChangeStateDialogComponent }       from './pages/change-state-dialog/change-state-dialog.component';


import { HeaderComponent }                  from './pages/header/header.component';
import { HomeComponent }                    from './pages/home/home.component';

import { AppComponent }                     from './app.component';
import { Service }                          from './service';


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

    ChangeStateDialogComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    ChartsModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule
  ],
  providers: [ 
    Service,
    AuthGuard,
    PaymentAuthGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
