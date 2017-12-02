import { Component, OnInit, Input, Output, EventEmitter, ViewChild, Renderer2 } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute }                                from '@angular/router';
import { trigger, state, style, animate, transition }                           from '@angular/animations';
import { Service }              from 'app/service';
import { Observable }                                       from 'rxjs/Rx'; 


@Component({
  selector: 'app-header',
  templateUrl: 'header.component.html',
  animations: [
    trigger(
      'modalAnimation', [

        transition(':enter',
          [
            style({opacity: 0, display: 'block'}),
            animate( '500ms', style({opacity: 1}) )
          ]
        ),
        transition(':leave',
          [
            style({  }),
            animate( '500ms', style({opacity: 0}) )
          ]
        )
      ]
    )
  ],
})

export class HeaderComponent implements OnInit  {

  hide:boolean = true;
  loggin:boolean = true;
  logo:string = "";
  nombre:string = "";
  show="";

  constructor( private router: Router, private route : ActivatedRoute, private service: Service){
          this.logo = localStorage.getItem('logo');
          this.nombre = localStorage.getItem('empresa');
  }


getServerExport(){
  return this.service.getServer() + 'api/swagger-ui.html#!/Export32CSV/exportCSVUsingPOST'
}

getServerImport(){
  return this.service.getServer() + 'api/swagger-ui.html#!/Import32CSV/importCSVUsingPOST'
}

changeState(){
  localStorage.clear();
  this.router.navigateByUrl('/home');

}

ngOnInit(): void { 

    Observable.interval(500).subscribe(
      x => { 

        
        if (this.router.url=='/login' || this.router.url=='/home'){
          this.hide = true
        }
        else
          this.hide = false;


        if(localStorage.getItem('token')){
          this.loggin = false;
        }
        else
          this.loggin = true;

    }); 

};

}
 