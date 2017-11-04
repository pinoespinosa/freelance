import { Pago }                    from 'app/data-objects/pago';


export class Trabajo {
  constructor(

    public id: string,
    public tema: string,
    public titulo: string,
    public universidad : string,
    public monto : string,
    public entrega : string,
    public estado : string,
    public pagos: Pago[]

  ) { }
}
