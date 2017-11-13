package data;

import java.util.ArrayList;
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
	private List<Requerimiento> requerimientos;

	private String fecha;
	private String fecha_entrega;

	private String observaciones;
	private String observaciones_next;

	private String asesor;

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

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getObservaciones_next() {
		return observaciones_next;
	}

	public void setObservaciones_next(String observaciones_next) {
		this.observaciones_next = observaciones_next;
	}

	public List<Requerimiento> getRequerimientos() {
		if (requerimientos == null)
			requerimientos = new ArrayList<>();

		return requerimientos;
	}

	public void setRequerimientos(List<Requerimiento> requerimientos) {
		this.requerimientos = requerimientos;
	}

}
