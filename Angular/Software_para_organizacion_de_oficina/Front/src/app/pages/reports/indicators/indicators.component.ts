import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';
import { Service }                                    from 'app/service';


@Component({
  selector: 'indicators',
  templateUrl: 'indicators.component.html',
  animations: [
  ],
})

export class ReportsIndicatorsComponent  implements OnInit  {


  arrayDias: Number[];
  public lineChartData:Array<any> = [ {data: [], label: 'Series A'} ];
  public lineChartLabels:Array<any> = ['Semana 1', 'Semana 2', 'Semana 3', 'Semana 4', 'Semana 5', 'Semana 6', 'Semana 7'];
  public lineChartOptions:any = {  responsive: true  };

  public lineChartData2:Array<any> = [ {data: [], label: 'Series A'} ];
  public lineChartData3:Array<any> = [ {data: [], label: 'Series A'} ];



  ngOnInit(): void {



    this.service.getSalesGraphNewClientsCash(30, 7).subscribe(
      response => {
        this.arrayDias = response;
        this.lineChartData = [ {data: this.arrayDias, label: 'Monto de Venta'} ];

      }        
    );
     
    this.service.getSalesGraphOldClientsCash(30, 7).subscribe(
      response => {
        this.arrayDias = response;
        this.lineChartData2 = [ {data: this.arrayDias, label: 'Monto de Venta'} ];

      }        
    );

    this.service.getSalesGraphNewClientsAmmount(30, 7).subscribe(
      response => {
        this.arrayDias = response;
        this.lineChartData3 = [ {data: this.arrayDias, label: 'Cantidad de clientes'} ];

      }        
    );


  };




  constructor( private router: Router, private route : ActivatedRoute, private service: Service ) {
      this.arrayDias = [];
  }

  public lineChartColors:Array<any> = [
    { // grey
      backgroundColor: 'rgba(148,159,177,0.2)',
      borderColor: 'rgba(148,159,177,1)',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    },
    { // dark grey
      backgroundColor: 'rgba(77,83,96,0.2)',
      borderColor: 'rgba(77,83,96,1)',
      pointBackgroundColor: 'rgba(77,83,96,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(77,83,96,1)'
    },
    { // grey
      backgroundColor: 'rgba(148,159,177,0.2)',
      borderColor: 'rgba(148,159,177,1)',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    }
  ];
  public lineChartLegend:boolean = true;
  public lineChartType:string = 'line';
 
  public showChart():void {
    
    this.lineChartData = [ {data: this.arrayDias, label: 'Series D'} ];
  }
 
  // events
  public chartClicked(e:any):void {
    console.log(e);
  }
 
  public chartHovered(e:any):void {
    console.log(e);
  }

  getData(cantDias, cantVal): void {


  }
}
 