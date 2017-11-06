import { Trabajo }                    from 'app/data-objects/trabajo';

export class Client {
  constructor(

    public id: string,

    public nombre: string,
    public apellido: string,

    public email1: string,
    public email2: string,
    public email3: string,
    public email4: string,

    public telefono1: string,
    public telefono2: string,
    public telefono3: string,
    public telefono4: string,

    public trabajos: Trabajo[],

  ) { }
}
