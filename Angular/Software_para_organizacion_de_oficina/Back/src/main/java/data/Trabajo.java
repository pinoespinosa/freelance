package data;

import java.util.List;

public class Trabajo {

	private String id;
	private String titulo;
	private String tema;
	private String estado;

	private String monto;
	private String carrera;
	private String universidad;
	private String dondeSeEntero;
	private String entrega;
	private String requerimientos;

	private String asesor;

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass().equals(Trabajo.class)) {
			Trabajo t = (Trabajo) obj;
			return t.getId().equals(getId());
		}
		return false;
	}

	private List<Pago> pagos;
	
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


	public String getRequerimientos() {
		return requerimientos;
	}

	public void setRequerimientos(String requerimientos) {
		this.requerimientos = requerimientos;
	}

	public List<Pago> getPagos() {
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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMonto() {
		return monto;
	}

	public void setMonto(String monto) {
		this.monto = monto;
	}

	public String getEntrega() {
		return entrega;
	}

	public void setEntrega(String entrega) {
		this.entrega = entrega;
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

}
