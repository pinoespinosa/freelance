import { Acta }                    from 'app/data-objects/acta';

export class CuerpoColegiado {
  constructor(

    public id: string,
    public nombre:string,
    public actas:Acta[],
  ) { }
}
