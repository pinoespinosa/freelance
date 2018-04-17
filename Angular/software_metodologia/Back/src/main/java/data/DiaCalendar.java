package data;

import java.util.List;

public class DiaCalendar {

	private String detalle;
	private List<Tarea> tareas;
	
	public List<Tarea> getTareas() {
		return tareas;
	}
	public void setTareas(List<Tarea> tareas) {
		this.tareas = tareas;
	}
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	
}
