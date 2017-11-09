import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { HeaderComponent } from   './pages/header/header.component';
import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './pages/register/register.component';
import { RegisterWorkComponent } from './pages/register-work/register-work.component';
import { RegisterPaymentComponent } from './pages/register-payment/register-payment.component';

import { MoreDetailsComponent } from './pages/more-details/more-details.component';

import { ReportsToDoComponent } from './pages/reports/to-do/to-do.component';
import { ReportsIncomesComponent } from './pages/reports/incomes/incomes.component';
import { ReportsNewClientsComponent } from './pages/reports/new-clients/new-clients.component';
import { ReportsOldClientsComponent } from './pages/reports/old-clients/old-clients.component';
import { ReportsIndicatorsComponent } from './pages/reports/indicators/indicators.component';



import { AppComponent } from './app.component';
import {HttpClientModule} from '@angular/common/http';
import { HttpModule } from '@angular/http';

import { AppRoutingModule }                   from './routing/app-routing.module';


import { Service }     from './service';


@NgModule({
  declarations: [
    HeaderComponent,
    AppComponent,
    RegisterComponent,
    RegisterWorkComponent,
    RegisterPaymentComponent,
    MoreDetailsComponent,
    HomeComponent,
    ReportsToDoComponent,
    ReportsIncomesComponent, 
    ReportsNewClientsComponent,
    ReportsOldClientsComponent,
    ReportsIndicatorsComponent

  ],
  imports: [
    BrowserModule,
    HttpModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [ 


  Service


   ],
  bootstrap: [AppComponent]
})
export class AppModule { }
