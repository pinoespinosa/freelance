export class Auditoria {
  constructor(

    public id: string,
    public fecha:string,
    public usuario: string,
    public accion: string,
    public rol : string

  ) { }
}
