import { Pago }                    from 'app/data-objects/pago';
import { Requerimiento }           from 'app/data-objects/requerimiento';


export class Trabajo {
  constructor(

    public id: string,

    public fecha: string,
    public tema: string,
    public universidad : string,
    public carrera: string,
    public asesor: string,
    public dondeSeEntero: string,

    public estado: string,
    public fecha_entrega: string,

    public monto: string,
    public saldo: string,

    public notas: string,
    
    public requerimientos: Requerimiento[],
    public pagos: Pago[]

  ) { }
}
