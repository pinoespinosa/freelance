package data;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Empresa {

	private String id;
	private List<CuerpoColegiado> colegiados;
	private Hashtable<String, Tema> temas = new Hashtable<>();

	private List<String> estrategias = new ArrayList<>();
	private List<String> indicador = new ArrayList<>();
		
	private String nombreEmpresa;
	private String logoEmpresa;

	
	public List<CuerpoColegiado> getColegiados() {
		if (colegiados==null)
			colegiados=new ArrayList<>();
		
		return colegiados;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj.getClass().equals(Empresa.class)) {
			Empresa t = (Empresa) obj;
			return t.getId().equals(getId());
		}
		return false;
	}
	
	public void setColegiados(List<CuerpoColegiado> colegiados) {
		this.colegiados = colegiados;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogoEmpresa() {
		return logoEmpresa;
	}

	public void setLogoEmpresa(String logoEmpresa) {
		this.logoEmpresa = logoEmpresa;
	}

	public Hashtable<String, Tema> getTemas() {
		if (temas == null)
			temas = new Hashtable<>();
		return temas;
	}

	public void setTemas(Hashtable<String, Tema> temas) {
		this.temas = temas;
	}

	public List<String> getEstrategias() {
		if (estrategias==null)
			estrategias= new ArrayList<>();
		return estrategias;
	}

	public void setEstrategias(List<String> estrategias) {
		this.estrategias = estrategias;
	}

	public List<String> getIndicador() {
		if (indicador==null)
			indicador=new ArrayList<>();
		return indicador;
	}

	public void setIndicador(List<String> indicador) {
		this.indicador = indicador;
	}

}
