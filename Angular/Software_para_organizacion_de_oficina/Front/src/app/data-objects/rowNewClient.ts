import { Pago }                    from 'app/data-objects/pago';

export class RowNewClient{
  constructor(

    public clienteID: string,
    public trabajoID: string,
    public pagoID:string,

    public nombre: string,
    public fechasuscripcion: string,
    public tema: string,
    public abono: string,
    public fecha_pago:string,
    public cantida_abonada: string,

    public nuevo: boolean,

  ) { }
}
