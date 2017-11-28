import { UsuarioActa }                    from 'app/data-objects/usuarioActa';


export class Acta {
  constructor(

    public id: string,
    
    public numeroActa:string,
    public fecha: string,
    public lugar: string,
    public ciudad : string,

    public integrantes:UsuarioActa[],
    public finMenteGral: string,
    public finMenteEsp: string,
    public temas : string[]

  ) { }
}



