import { Tarea }                    from 'app/data-objects/tarea';
import { Evento }                    from 'app/data-objects/evento';


export class Tema {
  constructor(

    public id: string,
    public estado:string,
    public detalle:string,
    public eventos: Evento[],
    public tareas: Tarea[],

  ) { }
}
