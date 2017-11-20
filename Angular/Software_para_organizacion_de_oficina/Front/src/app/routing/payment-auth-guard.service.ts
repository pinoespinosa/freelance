import { Injectable }                                                           from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot }     from '@angular/router';


@Injectable()
export class PaymentAuthGuard implements CanActivate {


  constructor(private router: Router) {}



  canActivate() {

    if(localStorage.getItem('token'))
    {

      if (localStorage.getItem('rol') == 'GERENTE'){
        return true;
      }
      else
      {
        this.router.navigate(['/home']);
        return false;
      }
    }
    else
    {
      this.router.navigate(['/login']);
      return false;
    }  

    }



}
