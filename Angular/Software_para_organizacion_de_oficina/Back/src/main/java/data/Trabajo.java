package data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Trabajo {

	private String id;

	private String fecha;
	private String tema;
	private String universidad;
	private String carrera;
	private String asesor;
	private String dondeSeEntero;

	private String estado;
	private String fecha_entrega;

	private String monto;
	private String saldo;
	
	private List<Requerimiento> requerimientos;
	private List<Pago> pagos;

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass().equals(Trabajo.class)) {
			Trabajo t = (Trabajo) obj;
			return t.getId().equals(getId());
		}
		return false;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public String getUniversidad() {
		return universidad;
	}

	public void setUniversidad(String universidad) {
		this.universidad = universidad;
	}

	public String getDondeSeEntero() {
		return dondeSeEntero;
	}

	public void setDondeSeEntero(String dondeSeEntero) {
		this.dondeSeEntero = dondeSeEntero;
	}

	public List<Pago> getPagos() {
		if (pagos == null)
			pagos = new ArrayList<>();

		return pagos;
	}

	public void setPagos(List<Pago> pagos) {
		this.pagos = pagos;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMonto() {
		return monto;
	}

	public void setMonto(String monto) {
		this.monto = monto;
	}


	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getAsesor() {
		return asesor;
	}

	public void setAsesor(String asesor) {
		this.asesor = asesor;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getFecha_entrega() {
		return fecha_entrega;
	}

	public void setFecha_entrega(String fecha_entrega) {
		this.fecha_entrega = fecha_entrega;
	}

	public List<Requerimiento> getRequerimientos() {
		if (requerimientos == null)
			requerimientos = new ArrayList<>();

		return requerimientos;
	}

	public void setRequerimientos(List<Requerimiento> requerimientos) {
		this.requerimientos = requerimientos;
	}

	public String getSaldo() {	
		return saldo;
	}

	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}
	
	public String toCSV(){
		
		String tex = "";
		for (int i = 0; i < 10; i++) {
			if (i < pagos.size())
				tex += '"' + pagos.get(i).getAbono() + '"' + ';' + '"' + pagos.get(i).getFecha_pago() + '"' + ';';
			else
				//tex += '"' + "-" + '"' + ',' + '"' + "-" + '"' + ',';
				tex += "\"-\";\"-\";";

		}

		String tex2 = "";
		for (int i = 0; i < 10; i++) {
			if (i < pagos.size())
				tex2 += '"' + pagos.get(i).getForma_pago() + '"' + ';' + '"' + pagos.get(i).getDetalle() + '"' + ';';
			else
				tex2 += "\"-\";\"-\";";
		}
		
		List<String> list = Arrays.asList(
				getTema(), 
				getCarrera(),
				getUniversidad(),
				getDondeSeEntero(),
				getFecha_entrega(),
				"-",
				getAsesor(),
				getEstado(),
				"observ",
				getMonto());

		System.out.println(tex);	
		System.out.println(tex2);
		System.out.println();
		System.out.println();
		return String.join("\";\"", list) + "\";" + tex + tex2;
				
	}

}
