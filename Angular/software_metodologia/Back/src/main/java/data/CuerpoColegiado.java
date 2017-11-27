package data;

import java.util.ArrayList;
import java.util.List;

public class CuerpoColegiado {

	private String id;
	private String nombre;
	private List<Acta> actas;


	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

	public List<Acta> getActas() {
		if (actas==null)
			actas = new ArrayList<>();
		return actas;
	}

	public void setActas(List<Acta> actas) {
		this.actas = actas;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj.getClass().equals(CuerpoColegiado.class)) {
			CuerpoColegiado t = (CuerpoColegiado) obj;
			return t.getId().equals(getId());
		}
		return false;
	}	
	
}
