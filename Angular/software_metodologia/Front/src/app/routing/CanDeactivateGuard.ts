import { CanDeactivate } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/delay';

export class CanDeactivateGuard {

  canDeactivate() {
    console.log('canDeactivate called');
    return Observable.of(false).delay(1000);
    //return false;
  }

}

