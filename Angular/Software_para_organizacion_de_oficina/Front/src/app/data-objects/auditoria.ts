export class Auditoria {
  constructor(

    public id: string,
    public fecha_pago:string,
    public usuario: string,
    public accion: string,
    public rol : string

  ) { }
}
