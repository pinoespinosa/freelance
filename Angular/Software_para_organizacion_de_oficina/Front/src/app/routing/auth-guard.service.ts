import { Injectable }                                                           from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot }     from '@angular/router';


@Injectable()
export class AuthGuard implements CanActivate {
  constructor(private router: Router) {}

  private isLoggedIn:boolean = false;

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    

    if(localStorage.getItem('token')){
      return true
    }
    else
    {
      console.log("Fui rebotado")
      this.router.navigate(['/login']);
      return false;
    }




  }
}
