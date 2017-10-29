import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { HeaderComponent } from   './pages/header/header.component';
import { RegisterComponent } from './pages/register/register.component';
import { HomeComponent } from './pages/home/home.component';



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
    HomeComponent

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
