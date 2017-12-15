import { CuerpoColegiado }                    from 'app/data-objects/cuerpoColegiado';

export class CuerpoColegiadoSelect extends CuerpoColegiado{
  check: boolean;
  constructor(a,b,c) { 
  super(a,b,c);
  this.check = false;
  }
}