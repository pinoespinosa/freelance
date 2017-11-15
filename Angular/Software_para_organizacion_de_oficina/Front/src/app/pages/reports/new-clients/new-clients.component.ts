import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';

import { Service }   			          from 'app/service';
import { HomeComponent }               from 'app/pages/home/home.component';

import { Client }                   from 'app/data-objects/cliente';
import { Requerimiento }               from 'app/data-objects/requerimiento';

import { RowNewClient}					from 'app/data-objects/rowNewClient';

@Component({
  selector: 'new-clients',
  templateUrl: 'new-clients.component.html',
  animations: [
  ],
})

export class ReportsNewClientsComponent  {


  title = 'app';
  clientes : Client[];
  requerimientos: string[];

  showDialogAddReq = false;
  showDialogChangeDate = false;

  tipo_orden = 1;

  cliente: Client;
  cliente2: RowNewClient;



  items : RowNewClient[];
  items_orig : RowNewClient[];

  estados: string[] = ['Urgente', 'Asignado','Trabajando','Listo por revisar','Avance Asesor Listo', 'Listo por pagar/enviar', 'Pendiente para t/ luego'];

  esstado:string;


  showDialog = false;


  callType(est){
    this.esstado = est;

  }




  sortNombre(){
    this.items.sort((a, b) => {
      if ( (a.nombre) < (b.nombre) ) 
        return -1 * this.tipo_orden;
      else 
        if ((a.nombre) > (b.nombre) ) 
          return 1 * this.tipo_orden;
        else 
          return 0;
    });
    this.tipo_orden = this.tipo_orden * -1
  }



	filterClientes(fechaDesde, fechaHasta){
		console.log("update front")
    	this.items=[];
    	this.items_orig = [];

        let loading = this.service.getClientsFiltred(fechaDesde.value, fechaHasta.value).subscribe(
            response => {
                this.clientes = response;

                for (var aa of response){
                  for (var bb of aa.trabajos){

                  	let valor:boolean;
                  	if (bb.pagos){

                  		if (bb.pagos[0].id.endsWith('-0')){
                  			console.log("TRUE" + bb.pagos[0].id);
                  			valor = true;
                  		}
                  		else{
                  			console.log("FALSE" + bb.pagos[0].id);
                  			valor = false;
                  		}

                  	}

                    for (var cc of bb.pagos){

                  	let resta = +bb.monto - +bb.saldo

                    let fu = new RowNewClient(aa.id, bb.id, cc.id, aa.nombre, aa.fechaSuscripcion, bb.tema, resta.toString(), cc.fecha_pago, cc.abono, valor);
                    this.items.push(fu);
                    this.items_orig.push(fu);
                  }
                }
}
            }        
        );

	}
 


  myFunction(valor){
    
    this.items = [];
    console.log(valor)
    for (var aa of this.items_orig){

      if ( aa.nombre.toLowerCase().includes(valor.toLowerCase()) ||  aa.tema.toLowerCase().includes(valor.toLowerCase()) ){
        this.items.push(aa);
      }
    } 
  }


  constructor(private router: Router, private service: Service){

    this.filterClientes("","");
    this.items=[];
    this.items_orig = [];
    this.cliente = new Client("","","", "","","", "","","",null);
    this.cliente2 = new RowNewClient("","","","","", "","","","",true);


}

}
 