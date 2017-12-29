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
import { HeaderComponent }                  from './pages/header/header.component';
import { HomeComponent }                    from './pages/home/home.component';

import { RegisterComponent }                from './pages/register/register.component';
import { RegisterUserComponent }            from './pages/register-user/register-user.component';
import { RegisterAsesorComponent }          from './pages/register-asesor/register-asesor.component';
import { RegisterWorkComponent }            from './pages/register-work/register-work.component';
import { RegisterPaymentComponent }         from './pages/register-payment/register-payment.component';
import { RegisterAllComponent }             from './pages/register-all/register-all.component';

import { MoreDetailsComponent }             from './pages/more-details/more-details.component';
import { ChangeDateComponent }              from './pages/change-date/change-date.component';
import { AuditsComponent }                  from './pages/audits/audits.component';

import { ReportsToDoComponent }             from './pages/reports/to-do/to-do.component';
import { ReportsNewClientsComponent }       from './pages/reports/new-clients/new-clients.component';
import { ReportsOldClientsComponent }       from './pages/reports/old-clients/old-clients.component';
import { ReportsIndicatorsComponent }       from './pages/reports/indicators/indicators.component';

import { DialogComponent }                  from './pages/dialog.component';
import { ChangeStateDialogComponent }       from './pages/change-state-dialog/change-state-dialog.component';

import { AppComponent }                     from './app.component';
import { Service }                          from './service';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    LoginComponent,
    DialogComponent,
    HeaderComponent,
    AppComponent,

    RegisterComponent,
    RegisterWorkComponent,
    RegisterPaymentComponent,
    RegisterUserComponent,
    RegisterAsesorComponent,
    RegisterAllComponent,

    HomeComponent,
    MoreDetailsComponent,
    
    ReportsToDoComponent,
    AuditsComponent, 
    ReportsNewClientsComponent,
    ReportsOldClientsComponent,
    ReportsIndicatorsComponent,
    
    ChangeDateComponent,
    ChangeStateDialogComponent

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
    PaymentAuthGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
