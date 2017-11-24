import { Trabajo }                    from 'app/data-objects/trabajo';

export class Client {
  constructor(

    public id: string,
    public nombre: string,
    public fechaSuscripcion: string,

    public email1: string,
    public email2: string,
    public email3: string,

    public telefono1: string,
    public telefono2: string,
    public telefono3: string,

    public trabajos: Trabajo[],

  ) { }
}
