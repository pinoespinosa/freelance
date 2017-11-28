import { Injectable }                                                           from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot }     from '@angular/router';


@Injectable()
export class AuthGuard implements CanActivate {


  constructor(private router: Router) {}



  canActivate() {
    return true;

/*
    if(localStorage.getItem('token'))
    {
      return true;
    }
    else
    {
      this.router.navigate(['/login']);
      return false;
    }  
*/
    }



}
