package data;

import java.util.ArrayList;
import java.util.List;

public class Tarea {

	private String id;
	private String estado;
	private String detalle;
	
	private List<String> eventos;

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

	public List<String> getEventos() {
		if (eventos==null)
			eventos = new ArrayList<String>();
		return eventos;
	}

	public void setEventos(List<String> eventos) {
		this.eventos = eventos;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass().equals(Tarea.class)) {
			Tarea t = (Tarea) obj;
			return t.getId().equals(getId());
		}
		return false;
	}


}
