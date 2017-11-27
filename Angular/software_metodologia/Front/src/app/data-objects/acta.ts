export class Acta {
  constructor(

    public id: string,
    
    public numeroActa:string,
    public fecha: string,
    public lugar: string,
    public ciudad : string,

    public integrantes:string[],
    public finMenteGral: string,
    public finMenteEsp: string,
    public temasNuevos : string,
    public temasTratados : string

  ) { }
}



