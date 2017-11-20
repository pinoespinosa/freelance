import { Injectable }                                                           from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot }     from '@angular/router';


@Injectable()
export class AuthGuard implements CanActivate {


  constructor(private router: Router) {}



  canActivate() {

    if(localStorage.getItem('token'))
    {
      console.log("No Fui rebotado");
      return true;
    }
    else
    {
      console.log("Fui rebotado")
      this.router.navigate(['/login']);
      return false;
    }  

    }



}
