export class Tarea {
  constructor(

    public id: string,
    public estado:string,
    public detalle:string,

    public eventos: string[],

  ) { }
}
