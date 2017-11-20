import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { LoginComponent } from './pages/login/login.component';
import { HeaderComponent } from   './pages/header/header.component';
import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './pages/register/register.component';
import { RegisterWorkComponent } from './pages/register-work/register-work.component';
import { RegisterPaymentComponent } from './pages/register-payment/register-payment.component';

import { MoreDetailsComponent } from './pages/more-details/more-details.component';

import { ChangeDateComponent } from './pages/change-date/change-date.component';

import { ReportsToDoComponent } from './pages/reports/to-do/to-do.component';
import { ReportsIncomesComponent } from './pages/reports/incomes/incomes.component';
import { ReportsNewClientsComponent } from './pages/reports/new-clients/new-clients.component';
import { ReportsOldClientsComponent } from './pages/reports/old-clients/old-clients.component';
import { ReportsIndicatorsComponent } from './pages/reports/indicators/indicators.component';

import { DialogComponent }               from './pages/dialog.component';
import { ChangeStateDialogComponent }               from './pages/change-state-dialog/change-state-dialog.component';

import { ChartsModule } from 'ng2-charts';

import { AppComponent } from './app.component';
import {HttpClientModule} from '@angular/common/http';
import { HttpModule } from '@angular/http';

import { AppRoutingModule }                   from './routing/app-routing.module';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


import { Service }     from './service';


@NgModule({
  declarations: [
    LoginComponent,
    DialogComponent,
    HeaderComponent,
    AppComponent,
    RegisterComponent,
    RegisterWorkComponent,
    RegisterPaymentComponent,
    MoreDetailsComponent,
    ChangeDateComponent,
    HomeComponent,
    ReportsToDoComponent,
    ReportsIncomesComponent, 
    ReportsNewClientsComponent,
    ReportsOldClientsComponent,
    ReportsIndicatorsComponent,
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


  Service


   ],
  bootstrap: [AppComponent]
})
export class AppModule { }
