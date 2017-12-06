import { Trabajo }                      from 'app/data-objects/trabajo';

export class ClientFull{
  constructor(

    public clienteID: string,
    public nombre: string,
    public apellido: string,

    public id_trabajo: string,
    public tema: string,
    public titulo: string,
    public monto: string,
    public saldo: string,
    public carrera : string,
    public universidad : string,
	public entrega : string,
	public estado : string,
 	 	
    public ordenEstado : string,
    public asesor: string,

    public abono: string,


  ) { }
}
