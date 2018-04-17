import { UsuarioActa }                    from 'app/data-objects/usuarioActa';

export class Tarea {
  constructor(

    public id: string,
    public estado:string,
    public detalle:string,
    public eventos: string[],
    public responsable: UsuarioActa, 
    private tareaID: string,
    private fechaCreacion: string,

  ) { }
}
