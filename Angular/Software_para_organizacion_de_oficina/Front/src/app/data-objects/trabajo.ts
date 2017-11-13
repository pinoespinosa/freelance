import { Pago }                    from 'app/data-objects/pago';
import { Requerimiento }           from 'app/data-objects/requerimiento';


export class Trabajo {
  constructor(

    public id: string,
    public tema: string,
    public titulo: string,
    public universidad : string,
    public monto : string,
    public saldo : string,

    public entrega : string,
    public dondeSeEntero : string,
    public estado : string,

    public fecha : string,
    public fecha_entrega : string,



    public observaciones : string,
    public observaciones_next : string,
    public requerimientos: Requerimiento[],
    public pagos: Pago[]

  ) { }
}
