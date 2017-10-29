import { Component } from '@angular/core';
import { Service }   from './service';

import { Client }               from './DataObjects/cliente';



@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';
  clientes : Client[];


    constructor(private service: Service) {
    this.getWorks();

console.log(this.clientes)

    }




    getWorks(): void {

        let loading = this.service.getProducts().subscribe(
            response => {
                this.clientes = response;
            }        );


    }







}
