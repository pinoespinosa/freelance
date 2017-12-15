package data;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CuerpoColegiado {

	private String id;
	private String nombre;
	private String prefijoDocs;



	private List<Acta> actas;
	
	@JsonIgnore
    private Empresa empresa;
	
	@JsonIgnore
    private Hashtable<String, Tema> temas;  

	private List<String> temasId; 

	public CuerpoColegiado() {
	}

	public void updateTemas(Empresa e){
		
		empresa=e;
		
		for (String s : getTemasId()) {
			if (e.getTemas().containsKey(s))
				getTemas().put(s, e.getTemas().get(s));
		}
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


	public String getPrefijoDocs() {
		return prefijoDocs;
	}

	public void setPrefijoDocs(String prefijoDocs) {
		this.prefijoDocs = prefijoDocs;
	}
	
	@JsonIgnore
	public Hashtable<String, Tema> getTemas() {
		if (temas == null){
			temas = new Hashtable<String, Tema>(){

				@Override
				public synchronized Tema put(String key, Tema value) {
					if (!temasId.contains(key))
						temasId.add(key);
					empresa.getTemas().put(key, value);
					return super.put(key, value);
				}
				
			};
		}
		return temas;
	}
	
	@JsonIgnore
	public void setTemas(Hashtable<String, Tema> temas) {
		this.temas = temas;
	}

	public List<String> getTemasId() {
		if (temasId==null)
			temasId = new ArrayList<>();
		return temasId;
	}

	public void setTemasId(List<String> temasId) {
		this.temasId = temasId;
	}
	
	@JsonIgnore
	public Empresa getEmpresa() {
		return empresa;
	}

	@JsonIgnore
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

}
