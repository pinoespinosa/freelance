package data;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class CuerpoColegiado {

	private String id;

	private String nombre;
	private String prefijoDocs;

	private List<Acta> actas;
	private Hashtable<String, Tema> temas = new Hashtable<>();

	public CuerpoColegiado() {
	}

	public CuerpoColegiado(String id) {
		super();
		this.id = id;
	}

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
		if (actas == null)
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

	public Hashtable<String, Tema> getTemas() {
		return temas;
	}

	public void setTemas(Hashtable<String, Tema> temas) {
		this.temas = temas;
	}

	public String getPrefijoDocs() {
		return prefijoDocs;
	}

	public void setPrefijoDocs(String prefijoDocs) {
		this.prefijoDocs = prefijoDocs;
	}

}
