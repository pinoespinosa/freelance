import { Tarea }                    from 'app/data-objects/tarea';


export class Tema {
  constructor(

    public id: string,
    public estado:string,
    public detalle:string,
    public eventos: string[],
    public tareas: Tarea[],

  ) { }
}
