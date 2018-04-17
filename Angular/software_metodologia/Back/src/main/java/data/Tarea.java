package data;

import java.util.ArrayList;

public class Tarea {

	private String id;
	private String fechaCreacion;
	private String temaId;
	private String estado;
	private String detalle;
	private UsuarioActa responsable;
	private ArrayList<String> eventos;

	public Tarea() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public ArrayList<String> getEventos() {
		if (eventos == null)
			eventos = new ArrayList<String>();
		return eventos;
	}

	public void setEventos(ArrayList<String> eventos) {
		this.eventos = eventos;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public UsuarioActa getResponsable() {
		return responsable;
	}

	public void setResponsable(UsuarioActa responsable) {
		this.responsable = responsable;
	}

	public String getTemaId() {
		return temaId;
	}

	public void setTemaId(String temaId) {
		this.temaId = temaId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass().equals(Tarea.class)) {
			Tarea t = (Tarea) obj;
			return t.getId().equals(getId()) && 
					t.getTemaId().equals(getTemaId());
		}
		return false;
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

}
