package data;

import java.util.ArrayList;
import java.util.List;

public class Tema {

	private String id;
	private String estado;
	private String objetivoEstrategico;
	private String detalle;
	private List<Tarea> tareas;
	private List<Evento> eventos;

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

	public List<Evento> getEventos() {
		if (eventos==null)
			eventos = new ArrayList<Evento>();
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public List<Tarea> getTareas() {
		if (tareas==null)
			tareas = new ArrayList<>();
		return tareas;
	}

	public void setTareas(List<Tarea> tareas) {
		this.tareas = tareas;
	}

	public String getObjetivoEstrategico() {
		return objetivoEstrategico;
	}

	public void setObjetivoEstrategico(String objetivoEstrategico) {
		this.objetivoEstrategico = objetivoEstrategico;
	}


}
